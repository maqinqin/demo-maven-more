package com.git.cloud.handler.automation.os.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.common.Constants;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 检查 Open Stack 创建虚机所需底层资源
 *
 * @author w
 * @date 2018/09/19
 */
public class EnvironmentCheckHandler extends OpenstackCommonHandler {
    private static Logger logger = LoggerFactory.getLogger(EnvironmentCheckHandler.class);

    /**
     * 镜像列表
     * A list of image objects, as described by the Images Schema.
     */
    private static final String IMAGES = "images";
    /**
     * 下一页的链接
     * The URI for the next page of response. Will not be present on the last page of the response.
     */
    private static final String NEXT = "next";

    @Override
    public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
        String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
        HashMap<String, String> deviceParams;
        List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
        String logCommon = "";

        String openStackId = null;
        String openStackIp = null;
        String domainName = null;
        String azName = null;
        String vmGroupId = null;
        long vmCpu = 0;
        long vmRam = 0;
        int cpu = 0;
        int ram = 0;
        int vmCount = deviceIdList.size();

        String flavorId = null;
        String token = null;
        String projectId = null;
        String networkId = null;
        String imageId = null;
        String policie = null;
        String version = null;
        String manageOneIp = null;

        logCommon = "环境检查";
        logger.debug(logCommon + "开始...");

        for (int i = 0; i < deviceIdList.size(); i++) {
            deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
            openStackId = deviceParams.get("OPENSTACK_ID");
            openStackIp = deviceParams.get("OPENSTACK_IP");
            domainName = deviceParams.get("DOMAIN_NAME");
            token = deviceParams.get("TOKEN");
            // 虚拟机组
            vmGroupId = deviceParams.get("VM_GROUP_ID");
            azName = deviceParams.get("AVAILABILITY_ZONE");
            flavorId = deviceParams.get("FLAVOR_ID");
            projectId = deviceParams.get("PROJECT_ID");
            networkId = deviceParams.get("NETWORK_ID");
            imageId = deviceParams.get("IMAGE_ID");
            version = deviceParams.get("VERSION");
            manageOneIp = deviceParams.get("MANAGE_ONE_IP");

            break;
        }

        OpenstackIdentityModel model = new OpenstackIdentityModel();
        model.setVmMsId(openStackId);
        model.setDomainName(domainName);
        model.setOpenstackIp(openStackIp);
        model.setProjectName(Constants.getConstantsParameter(Constants.OPENSTACK_MANAGE_PROJECT));
        model.setVersion(version);
        model.setManageOneIp(manageOneIp);
        String managementProjectId = IaasInstanceFactory.identityInstance(version).getManageProject(model);
        model.setProjectId(managementProjectId);


        // 查询可用分区，判断是否存在
        boolean azExist = false;
        JSONArray res = IaasInstanceFactory.computeInstance(version).getAvailabilityZoneList(model);
        for (Object obj : res.toArray()) {
            JSONObject jsonObj = (JSONObject) obj;
            if (jsonObj.getJSONObject("zoneState").getBoolean("available")) {
                if (azName.equals(jsonObj.getString("zoneName"))) {
                    azExist = true;
                    break;
                }
            }
        }
        if (!azExist) {
            throw new Exception("Availability-zone : [" + azName + "]不存在.");
        }
        // 查询虚机模板，判断是否存在
        OpenstackIdentityModel floverModel = new OpenstackIdentityModel();
        floverModel.setVersion(version);
        floverModel.setOpenstackIp(openStackIp);
        floverModel.setDomainName(domainName);
        floverModel.setManageOneIp(manageOneIp);
        floverModel.setProjectId(managementProjectId);
        String flavorListJson = IaasInstanceFactory.computeInstance(version).getFlavor(floverModel);
        JSONArray flavorList = JSONObject.parseObject(flavorListJson).getJSONArray("flavors");
        Optional<Object> optionalflavorObj = Optional.empty();
        for (Object n : flavorList) {
            if (((JSONObject) n).getString("id").equals(flavorId)) {
                optionalflavorObj = Optional.of(n);
                break;
            }
        }
        if (!optionalflavorObj.isPresent()) {
            // 模板不存在
            throw new Exception("Flavor : [" + flavorId + "]不存在.");
        } else {
            // 获取虚拟机配置信息
            JSONObject flavorObj = (JSONObject) optionalflavorObj.get();
            vmCpu = flavorObj.getInteger("vcpus") * deviceIdList.size();
            vmRam = flavorObj.getInteger("ram") * deviceIdList.size();
            cpu = flavorObj.getInteger("vcpus");
            ram = flavorObj.getInteger("ram");
        }
        // 查询网络列表，判断是否存在
        model.setProjectId(projectId);
        String networkListJson = IaasInstanceFactory.networkInstance(version).getNetwork(model);
        JSONArray networkList = JSONObject.parseObject(networkListJson).getJSONArray("networks");
        Optional<Object> optionalNetworkObj = Optional.empty();
        for (Object n : networkList) {
            if (((JSONObject) n).getString("id").equals(networkId)) {
                optionalNetworkObj = Optional.of(n);
                break;
            }
        }
        if (!optionalNetworkObj.isPresent()) {
            // 网络不存在
            throw new Exception("Network : [" + networkId + "]不存在.");
        }

        for (int i = 0; i < deviceIdList.size(); i++) {
            deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
            String fixdIp = deviceParams.get("SERVER_IP");
            // 查询port列表, 判断IP是否被占用
            model.setProjectId(projectId);
            String portListJson = IaasInstanceFactory.networkInstance(version).getNetworkPort(model, networkId);
            JSONArray portList = JSONObject.parseObject(portListJson).getJSONArray("ports");

            Optional<Object> optionalPortObj = Optional.empty();
            for (Object n : portList) {
                JSONObject jObj = (JSONObject) n;
                JSONArray fixedIpList = jObj.getJSONArray("fixed_ips");
                Optional<Object> optionalIp = Optional.empty();
                for (Object ip : fixedIpList) {
                    if (((JSONObject) ip).getString("ip_address").equals(fixdIp)) {
                        optionalIp = Optional.of(ip);
                        break;
                    }
                }
                if (optionalIp.isPresent()) {
                    optionalPortObj = optionalIp;
                    break;
                }
            }
            if (optionalPortObj.isPresent()) {
                // IP 已经存在
                throw new Exception("IP : [" + fixdIp + "]已经被占用.");
            }
        }

        // 判断镜像是否存在
        boolean exist = false;
        // 获取镜像列表
        model.setProjectId(managementProjectId);
        String imageJson = IaasInstanceFactory.imageInstance(version).getImageList(model);
        // 转成json object
        JSONObject imageListObj = JSONObject.parseObject(imageJson);
        // 获取镜像列表
        JSONArray imagesArray = imageListObj.getJSONArray(IMAGES);
        // 将镜像数据保存到list
        List<String> imageIdList = new ArrayList<>();
        // 第一页
        for (Object o : imagesArray) {
            JSONObject jsonObject = (JSONObject) o;
            String id = jsonObject.getString("id");
            if (id.equals(imageId)) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            // 下一页页
            String next = imageListObj.getString(NEXT);
            // 当下一页存在时
            while (next != null && !"".equals(next)) {
                next = next.split("=")[1];
                String nextJsonStr = IaasInstanceFactory.imageInstance(version).getImageList(model, next);
                JSONObject nextImageListObj = JSONObject.parseObject(nextJsonStr);
                JSONArray nextImagesArray = nextImageListObj.getJSONArray(IMAGES);
                for (Object o : nextImagesArray) {
                    JSONObject jsonObject = (JSONObject) o;
                    String id = jsonObject.getString("id");
                    if (id.equals(imageId)) {
                        exist = true;
                        break;
                    }
                }
                if (exist) {
                    break;
                }
                next = nextImageListObj.getString(NEXT);
            }
        }
        if (!exist) {
            // 镜像不存在
            throw new Exception("Image : [" + imageId + "]不存在.");
        }
        // 如果使用虚拟机组
        if (vmGroupId != null && !"".equals(vmGroupId)) {
            // 获取compute的service
            model.setProjectId(projectId);
            String resJson = IaasInstanceFactory.computeInstance(version).getServerGroupDetails(model, vmGroupId);
            if (resJson == null) {
                throw new Exception("虚拟机组(os-server-groups) : [" + vmGroupId + "]不存在.");
            }
            JSONObject jsonObj = JSONObject.parseObject(resJson);
            JSONObject vmGroupObj = jsonObj.getJSONObject("server_group");
            JSONArray policies = vmGroupObj.getJSONArray("policies");
            if (policies.size() > 0) {
                policie = (String) policies.get(0);
            }
        }

        // 校验虚拟机所需CPU和内存
        checkHostRes(model, version, azName, vmCpu, vmRam, vmGroupId, vmCount, policie, cpu, ram);
        logger.debug(logCommon + "结束...");
    }

    /**
     * 校验虚拟机所需CPU和内存
     *
     * @param azName
     * @param vmCpu
     * @param vmRam
     * @param vmGroupId
     * @param vmCount
     * @throws Exception
     */
    private void checkHostRes(OpenstackIdentityModel model, String version, String azName, long vmCpu, long vmRam
            , String vmGroupId, int vmCount, String policie, int cpu, int ram) throws Exception {

        String managementProjectId = IaasInstanceFactory.identityInstance(version).getManageProject(model);
        model.setProjectId(managementProjectId);

        // 获取所有主机组 Host aggregates (os-aggregates)
        String hostGroupJson = IaasInstanceFactory.computeInstance(version).getGroupHost(model);
        // 解析返回的数据
        List<JSONObject> resJsonList = new ArrayList<>();
        JSONObject resJsonObj = JSONObject.parseObject(hostGroupJson);
        JSONArray aggregatesJsonObj = resJsonObj.getJSONArray("aggregates");
        for (Object obj : aggregatesJsonObj.toArray()) {
            JSONObject jsonObject = (JSONObject) obj;
            // 可用分区
            String az = jsonObject.getString("availability_zone");
            if (az.equals(azName)) {
                // 当可用分区与传入的可用分区相同时
                resJsonList.add(jsonObject);
            }
        }

        List<CmHostPo> hostList = new ArrayList<>();
        long totalCpu = 0;
        long totalRam = 0;

        for (JSONObject jsonObject : resJsonList) {
            // 主机组名称(集群名称)
            String name = jsonObject.getString("name");
            // uuid (因为目前接口得不到uuid这个值，先用name作为uuid)
            String uuid = jsonObject.getString("name");

            // hosts 遍历主机组里的host主机，找到主机资源的Id ; A list of host ids in this aggregate.
            JSONArray jsonHostArr = jsonObject.getJSONArray("hosts");
            for (Object hostObj : jsonHostArr.toArray()) {
                // host id in this aggregate
                String hostName = (String) hostObj;
                // 查询主机资源列表 Hypervisors (os-hypervisors)
                String hostResJson = IaasInstanceFactory.computeInstance(version).getGroupHostRes(model);
                if (hostResJson != null && hostResJson.trim().length() > 0) {
                    JSONObject hostGroupjson = JSONObject.parseObject(hostResJson);
                    JSONArray jsonArray = hostGroupjson.getJSONArray("hypervisors");
                    for (Object hObj : jsonArray.toArray()) {
                        JSONObject hJsonObj = (JSONObject) hObj;
                        // 遍历主机资源里的主机名称跟主机组内的名称匹配
                        // The hypervisor host name
                        String hHostName = hJsonObj.getString("hypervisor_hostname");
                        // The id of the hypervisor as a UUID.
                        String hId = hJsonObj.getString("id");
                        boolean isEquals = false;
                        if (hHostName.contains("@FCcluster")) {
                            // Show Hypervisor Details
                            String hostResDetailJson = IaasInstanceFactory.computeInstance(version).getGroupHostResDetail(model, hId);
                            JSONObject jso = JSONObject.parseObject(hostResDetailJson);
                            JSONObject hypervisor = jso.getJSONObject("hypervisor");
                            // The hypervisor service object. The name of the host.
                            String sHostName = hypervisor.getJSONObject("service").getString("host");
                            if (hostName.equals(sHostName)) {
                                isEquals = true;
                            }
                        } else if (hostName.equals(hHostName)) {
                            isEquals = true;
                        }
                        if (isEquals) {
                            // Show Hypervisor Details
                            String hostResDetailJson = IaasInstanceFactory.computeInstance(version).getGroupHostResDetail(model, hId);
                            if (hostResDetailJson != null && hostResDetailJson.trim().length() > 0) {
                                // 5.封装主机详细信息
                                // 获取超分值
                                // 查询CPU超分值
                                String overSplitJson = IaasInstanceFactory.networkInstance(version).getOverSplit(model);
                                JSONObject jso = JSONObject.parseObject(overSplitJson);
                                double parseDouble = 0.0f;
                                if ("6.3".equals(version)) {
                                    JSONArray hypervisors = jso.getJSONArray("hypervisors");
                                    JSONObject hJObj = null;
                                    for(Object obj : hypervisors) {
                                        JSONObject jObj = (JSONObject)obj;
                                        String id = jObj.getString("id");
                                        if(hId.equals(id)) {
                                            hJObj = jObj;
                                            parseDouble = hJObj.getDouble("cpu_allocation_ratio");
                                            break;
                                        }
                                    }
                                }
                                else {
                                    JSONObject cfg = jso.getJSONObject("cfg");
                                    String split = cfg.getString("cpu_allocation_ratio");
                                    parseDouble = Double.parseDouble(split);
                                }

                                JSONObject hostResDetailjson = JSONObject.parseObject(hostResDetailJson);
                                String str = hostResDetailjson.getString("hypervisor");
                                JSONObject obj = JSONObject.parseObject(str);
                                if (obj != null) {
                                    // host
                                    CmHostPo hostPo = new CmHostPo();
                                    // cpu和已使用的cpu
                                    Integer vcpu = (int) Math.floor(obj.getInteger("vcpus") * parseDouble);
                                    hostPo.setCpu(vcpu);
                                    // 内存和已使用的内存
                                    // int memory_mb =obj.getInteger("memory_mb")/1024;
                                    int memory_mb = obj.getInteger("memory_mb");
                                    // int memory_mb_used =obj.getInteger("memory_mb_used")/1024;
                                    int memory_mb_used = obj.getInteger("memory_mb_used");
                                    hostPo.setMem(memory_mb);
                                    hostPo.setCpuUsed(Integer.parseInt(obj.getString("vcpus_used")));
                                    hostPo.setMemUsed(memory_mb_used);

                                    totalCpu += vcpu - Integer.parseInt(obj.getString("vcpus_used"));
                                    totalRam += memory_mb - memory_mb_used;

                                    hostPo.setId(name);

                                    hostList.add(hostPo);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (totalCpu < vmCpu) {
            throw new Exception("物理机可用CPU总数 : [" + totalCpu + "]小于虚机所需CPU: [ " + vmCpu + " ].");
        }
        if (totalRam < vmRam) {
            throw new Exception("物理机可用内存总数 : [" + totalRam + "]小于虚机所需内存: [ " + vmRam + " ].");
        }
        // 如果使用虚拟机组
        if (vmGroupId != null && !"".equals(vmGroupId)) {
            // 物理机列表按可用量排序
            hostList.sort(new Comparator<CmHostPo>() {
                @Override
                public int compare(CmHostPo h1, CmHostPo h2) {
                    return (h2.getCpu() - h2.getCpuUsed() - (h1.getCpu() - h1.getCpuUsed()));
                }
            });

            if (policie == null) {
                throw new Exception("虚拟机组(os-server-groups) : [" + vmGroupId + "]策略为空.");
            } else if ("anti-affinity".equals(policie)) {
                // servers in this group must be scheduled to different hosts.
                if (hostList.size() < vmCount) {
                    throw new Exception("虚拟机组(os-server-groups) : [" + vmGroupId + "]策略为反亲和性，物理机数量[" + hostList.size() + "]小于虚拟机数量[" + vmCount + "].");
                }
                // 获取最后一个虚机对应的物理机
                CmHostPo hostPo = hostList.get(vmCount - 1);
                if (hostPo.getCpu() - hostPo.getCpuUsed() < cpu) {
                    throw new Exception("物理机[" + hostPo.getId() + "]可用CPU : [" + (hostPo.getCpu() - hostPo.getCpuUsed()) + "]小于虚机所需CPU: [ " + cpu + " ].");
                }
                if (hostPo.getMem() - hostPo.getMemUsed() < ram) {
                    throw new Exception("物理机[" + hostPo.getId() + "]可用内存 : [" + (hostPo.getMem() - hostPo.getMemUsed()) + "]小于虚机所需内存: [ " + ram + " ].");
                }
            } else if ("affinity".equals(policie)) {
                // servers in this group must be scheduled to the same host.
                // 获取第一个物理机
                CmHostPo hostPo = hostList.get(0);
                if (hostPo.getCpu() - hostPo.getCpuUsed() < cpu) {
                    throw new Exception("物理机[" + hostPo.getId() + "]可用CPU : [" + (hostPo.getCpu() - hostPo.getCpuUsed()) + "]小于虚机所需CPU: [ " + cpu + " ].");
                }
                if (hostPo.getMem() - hostPo.getMemUsed() < ram) {
                    throw new Exception("物理机[" + hostPo.getId() + "]可用内存 : [" + (hostPo.getMem() - hostPo.getMemUsed()) + "]小于虚机所需内存: [ " + ram + " ].");
                }
            }

        }

    }
}
