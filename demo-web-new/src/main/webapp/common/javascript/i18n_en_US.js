var i18n_en_US = {
	'bm_sr_code' : 'Req No',//服务单号
	'rm_datacenter' : 'DC',//数据中心
	'app_info' : 'System',//应用系统
	'bm_sr_status' : 'Status',//申请状态
	'bm_sr_type' : 'Type',//申请类型
	'request_user' : 'Req User',//申请人
	'request_time' : 'Req time',//申请时间
	'com_operate' : 'Operation',//操作
	'com_tip' : 'Tip',
	'com_confirm' : 'Confirm',
	'com_cancel' : 'Cancel',
	'com_Y' : 'Yes',//i18nShow('com_Y')
	'com_N' : 'No',//i18nShow('com_N')
	//wmy start
	'host_res_device_name' : 'Host name',
	'host_res_seat_name' : 'Position',
	'host_res_datastore_name' : 'Datastore',
	'host_res_defaultDa_name' : 'Default datastore',
	'host_res_username' : 'Username',
	'host_res_password' : 'Password',
	'host_res_isBare' : 'Bare Metal ',
	'host_res_isBareY' : 'Bare machine',
	'host_res_isBarN' : 'Non bare',
	'host_res_option' : 'Set up',
	'host_res_bareV' : 'Value',
	'host_res_unmountDataTip1' : 'Make sure that the datastore is uninstalled from all devices',
	'host_res_unmountDataTip2' : 'Determine uninstall datastore',
	'host_res_unRefDataTip' : 'An exception occurred while canceling the associated datastore',
	'host_res_unRefDataTipSuccsee' : 'Uninstall successfully',
	'host_res_mountDataTip1' : 'Make sure that the mount select datastore is in all the accepted devices',
	'host_res_mountDataTip2' : 'Determine mount datastore',
	'host_res_mountDataSuccess' : 'Mount successfully',
	'host_res_mountDataFail' : 'Mount failure',
	'host_res_saveDataFail' : 'An exception occurred while saving the datastore info',
	'host_res_setUserPwd' : 'Set user name and password',
	'host_res_userTip' : 'User name cannot be empty',
	'host_res_userTip_len' : 'Max user name is 30',
	'host_res_pwdTip' : 'Password cannot be empty',
	'host_res_ipAdd' : 'The address cannot be empty',
	'host_res_ipAdd_tip' : 'Please fill in the IP value correctly',
	'host_res_version' : 'Version number cannot be empty',
	'host_res_showHardWareStatus' : 'Hardware',
	'host_res_showHardWareStatusErr' : 'An exception occurred while viewing the hardware status',
	'host_res_maintenance' : 'Determine access to maintenance mode',
	'host_res_maintenance_success' : 'Success in entry maintenance mode!',
	'host_res_exit_maintenance_tip' : 'Determine exit maintenance mode?!',
	'host_res_exit_maintenance_success' : 'Quit maintenance mode successfully!',
	'host_res_pmClose_tip' : 'Are you sure you want to turn off the Bare Metal?',
	'host_res_pmClose_success' : 'Bare Metal shutdown successful!',
	'host_res_pmReboot_tip' : 'Are you sure you want to restart the Bare Metal?',
	'host_res_pmReboot_success' : 'Bare Metal restart successfully!',
	'host_res_operateErr' : 'Operation exception occurred',
	'host_res_id' : 'Bare Metal ID',
	'compute_res_deviceId' : 'Device Id',
	'compute_res_deviceName' : 'Name',
	'compute_res_server':'Server Configuration',
	'res_l_comput_loginName':'Subordinate users',
	'res_l_comput_tenantName':'Tenant',
	'compute_res_deviceCpu' : 'CPU',
	'compute_res_deviceCpuUsed' : 'CPU used',
	'compute_res_deviceMem' : 'MEM(G)',
	'compute_res_isInvc' : 'Control',
	'compute_res_isInvcY' : 'Controlled',
	'compute_res_isInvcN' : 'Uncontrolled',
	'compute_res_deviceMemUsed' : 'MEM',
	'compute_res_vmTypeCode' : 'Virtual type',
	'compute_res_ipAddress' : 'IP',
	'compute_res_diskSpace' : 'Disk',
	'compute_res_cloudServiceName' : 'Cloud services',
	'compute_res_appCname' : 'Application',
	'compute_res_duName' : 'Unit ',
	'compute_res_platFormCode' : 'Platform type code',
	'compute_res_runningState' : 'Running state',
	'compute_res_poweron' : 'Running',
	'compute_res_poweroff' : 'Closed',
	'compute_res_poweroff1' : 'Power off',
	'compute_res_hang' : 'Suspend',
	'compute_res_unknown' : 'Unknown',
	'compute_res_select' : 'Please select',
	'compute_res_dataCenterId' : 'DC ID',
	'compute_res_respoolId' : 'Resource pool ID',
	'compute_res_vmId' : 'VM ID',
	'compute_res_vmName' : 'VM name',
	'compute_res_login' : 'Sign in',
	'compute_res_login_ipTip' : 'Please enter IP',
	'compute_res_login_version' : 'Please select version',
	'compute_res_login_pwd' : 'Please input a password',
	'compute_res_login_err' : 'Please wait a moment',
	'compute_res_requestError' : 'Request was aborted',
	'compute_res_operateSuccess' : 'Successful operation',
	'compute_res_operateFail' : 'Operation failed',
	'compute_res_device_poweron' : 'Starting up',
	'compute_res_device_poweron_tip' : 'Are you sure to turn on the machine?',
	'compute_res_device_poweron_success' : 'Boot successfully',
	'compute_res_device_poweron_fail' : 'Boot failure',
	'compute_res_device_poweroff' : 'Shutdown',
	'compute_res_device_poweroff_tip' : 'Are you sure to shutdown?',
	'compute_res_device_poweroff_success' : 'Shutdown successful',
	'compute_res_device_poweroff_fail' : 'Shutdown failure',
	'compute_res_device_restart' : 'Restart',
	'compute_res_device_restart_tip' : 'Sure to restart?',
	'compute_res_device_restart_success' : 'Restart successfully',
	'compute_res_device_restart_fail' : 'Restart failed',
	'compute_res_device_remoteControl' : 'Remote',
	'compute_res_device_remoteControl_tip' : 'Are you sure you want to open the VNC console?',
	'compute_res_device_forcedRestart' : 'Forced restart',
	'compute_res_device_forcedRestart_tip' : 'Are you sure you need to restart?',
	'compute_res_device_forcedRestart_success' : 'Forced restart successfully',
	'compute_res_device_forcedRestart_fail' : 'Forced restart failed!',
	'compute_res_device_mountVolume' : 'Mount volume',
	'compute_res_device_mountVolume_tip' : 'Are you sure you want to create a volume? In this process, you may need to wait a little...',
	'compute_res_device_createVolume_success' : 'Creates and mounts volume successfully',
	'compute_res_device_mountVolume_fail' : 'Failed to create and mount the volume!',
	'compute_res_device_transfer' : 'Transfer',
	'compute_res_device_mountVolume_tip' : 'Are you sure you mount the volume?',
	'compute_res_device_mountVolume_success' : 'Mount volume successfully',
	'compute_res_device_mountVolume_fail' : 'Failed to mount volume!',
	'compute_res_device_unloadVolume' : 'Unload volume',
	'compute_res_device_unloadVolume_tip' : 'Are you sure you want to uninstall the volume?',
	'compute_res_device_unloadVolume_success' : 'Uninstall volume successful',
	'compute_res_device_unloadVolume_fail' : 'Failed to unload the volume!',
	'compute_res_device_mountDiskNumbe' : 'block',
	'compute_res_volumeName' : 'Volume name',
	'compute_res_volumeId' : 'Volume ID',
	'compute_res_volume_diskSize' : 'Capacity(G)',
	'compute_res_volume_diskType' : 'Type',
	'compute_res_volume_exterDisk' : 'Disk',
	'compute_res_volume_size' : 'Size',
	'compute_res_volume_available' : 'Available',
	'compute_res_volume_bound' : 'Bound',
	'compute_res_volume_azName' : 'Available partition',
	'compute_res_volume_isShare' : 'Share',
	'compute_res_volume_shareY' : 'Shared',
	'compute_res_volume_shareN' : 'Non shared',
	'compute_res_volume_status' : 'Status',
	'compute_res_volume_mount' : 'Mounted',
	'compute_res_volume_unmont' : 'Unmounted',
	'compute_res_rimaryKey_id' : 'Primary key ID',
	'compute_res_device_hang' : 'Hang',
	'compute_res_device_hang_tip' : 'Are you sure to hang up?',
	'compute_res_device_hang_success' : 'Hang up successful',
	'compute_res_device_hang_fail' : 'Suspend failed',
	'compute_res_device_createSnapshot' : 'Create snapshot',
	'compute_res_device_snapshotManage' : 'Snapshot manager',
	'compute_res_device_snapshotTip' : 'The snapshot name cannot be empty',
	'compute_res_device_createSnapshotTip' : 'Confirm create new snapshot',
	'compute_res_device_createSnapshotSuccess' : 'Create snapshot success!',
	'compute_res_device_createSnapshotFail' : 'Failed to create snapshot!',
	'compute_res_device_snapshot_nameExisted' : 'The snapshot name already exists',
	'compute_res_device_snapshot_name' : 'Snapshot name',
	'compute_res_device_recoverSnapshot_tip' : 'Make sure to restore snapshot?',
	'compute_res_device_recoverSnapshot_success' : 'recovery was successful',
	'compute_res_device_recoverSnapshot_fail' : 'restore failed',
	'compute_res_device_delSnapshot_tip' : 'Confirm delete snapshot?',
	'compute_res_device_snapSilence' : 'Silent installation',	
	'compute_res_device_snapMemory' : 'MEM snapshot',	
	'compute_res_device_snapRecovery' : 'Recovery',	
	'compute_res_device_snapDelete' : 'Delete',	
	'compute_res_creator' : 'Creator',
	'compute_res_PowerStatus' : 'Power status',
	'compute_res_createDate' : 'Creation time',
	'compute_res_dbFail' : 'Database operation failed',
	'compute_res_delSuccess' : 'Delete successfully!',
	'compute_res_delFail' : 'Delete failed!',
	'compute_res_device_awaken' : 'Awaken',
	'compute_res_device_awaken_tip' : 'Do you want to wake up this VM?',
	'compute_res_device_awaken_success' : 'Wake up successfully',
	'compute_res_device_awaken_fail' : 'Wakeup failure',
	'compute_res_selectDu' : 'Select units',
	'compute_res_duId' : 'Unit ID',
	'compute_res_appCName' : 'App CN abbr',
	'compute_res_appEName' : 'App EN abbr',
	'compute_res_duCName' : 'Unit CN abbr',
	'compute_res_duEName' : 'Unit',
	'compute_res_changeDu' : 'Change',
	'compute_res_changeDuTip' : 'Determines the change to the selected deployment unit?',
	'compute_res_updateSuccess' : 'Modify successfully',
	'compute_res_updateFail' : 'Modify failed',
	'compute_res_selectData' : 'Select at least one data',
	'compute_res_removeDevice_toCluster' : 'Are you sure you want to remove the connection to the cluster?',
	'compute_res_refDevice_toCluster' : 'Are you sure you want to connect to the cluster?',
	'compute_res_refDevice_toCluster_tip' : 'The number of Bare Metal under the cluster exceeds 200 units',
	'compute_res_refDevice_toCluster_tip2' : 'User name and password are not set',
	'compute_res_refDevice_toCluster_succss' : 'Association success',
	'compute_res_refDevice_toCluster_fail' : 'Association failure',
	'compute_res_removeDevice_success' : 'Remove successfully',
	'compute_res_removeDevice_fail' : 'Remove failed',
	'compute_res_inVcControl_success' : 'Host control successful!',
	'compute_res_inVcControl_fail' : 'Host control failed and returned info：',
	'compute_res_inVcControl_tip' : 'The Bare Metal number is beyond the allowable control range',
	'compute_res_inVcControl_tip1' : 'Is the operation determined?',
	'compute_res_outVcControl_tip' : 'Release control?',
	'compute_res_outVcControl_success' : 'Success！',
	'compute_res_outVcControl_hostnotExit' : 'The Bare Metal does not exist',
	'compute_res_outVcControl_alreadyOut' : 'This Bare Metal has been disabled on the Venter!',
	'compute_res_error' : 'Error occurred!',
	'compute_res_showDatastore' : 'Alternative DataStore',
	'compute_res_datastore_resPoolName' : 'Owned resource pool',
	'compute_res_datastore_storageDevice' : 'Owned device',
	'compute_res_datastore_storageType' : 'Storage type',
	'compute_res_datastore_orderNum' : 'Order',
	'compute_res_datastore_name' : 'Name',
	'compute_res_datastore_path' : 'Path',
	'compute_res_defaultDataStore' : 'Set as default',
	'compute_res_whetherDefault' : 'Default',
	'compute_res_whetherMount' : 'Mount',
	'compute_res_defaultDataStore_tip' : 'Are you sure to set as default datastore?',
	'compute_res_selectDataStore_tip' : 'Please select the default datastore',
	'compute_res_datastore_mount' : 'Mount',
	'compute_res_datastore_unmount' : 'Unmount',
	'compute_res_yes' : 'Yes',
	'compute_res_no' : 'No',
	'compute_res_nothing' : 'nothing',
	'compute_res_saveSuccess' : 'Save successfully!',
	'compute_res_saveFail' : 'Save failed!',
	'compute_res_validateCdpName' : 'The CDP name cannot be repeated',
	'compute_res_validateCdpEname' : 'CDP English code can not be repeated',
	'compute_res_validateCdpStringCheck' : 'Can only include English letters',
	'compute_res_cdpStringCheck' : '(letters, numbers, _, -)',
	'compute_res_cdpNot_empty' : 'CDP cannot be empty',
	'compute_res_cdpEnameNot_empty' : 'English code must not be empty',
	'compute_res_cdpDatail' : 'CDP details',
	'compute_res_cdp_update' : 'Modify CDP',
	'compute_res_cdp_add' : 'Create CDP',
	'compute_res_cdp_del_tip' : 'There is a cluster or a Bare Metal under current CDP and cannot be deleted!',
	'compute_res_cdp_del_tip2' : 'Please select one to delete!',
	'compute_res_selectError' : 'Query failed!',
	'compute_res_deleteData_tip' : 'Confirm delete data?',
	'compute_res_cluster_detail' : 'Cluster details',
	'compute_res_cluster_update' : 'Update cluster info',
	'compute_res_cluster_stringCheck' : 'Can only include English letters',
	'compute_res_cluster_clusterNameCheck' : 'Cluster name cannot be duplicated',
	'compute_res_cluster_clusterEnameCheck' : 'English code cannot be duplicated',
	'compute_res_cluster_name_notEmpty' : 'Cluster name cannot be empty',
	'compute_res_cluster_maxLength' : 'Length is not more than 30',
	'compute_res_cluster_vmType_notEmpty' : 'The VM type cannot be empty',
	'compute_res_cluster_vmDistriType_notEmpty' : 'The allocation type cannot be null',
	'compute_res_cluster_networkConvergence_notEmpty' : 'Network convergence can not be empty',
	'compute_res_cluster_save_tip1' : 'VM type is VMWare, the manage server can not be empty!',
	'compute_res_cluster_save_tip2' : 'The manage server and the standby manage server cannot choose the same!',
	'compute_res_cluster_add' : 'Create cluster',
	'compute_res_cluster_add_toRespool' : 'Create cluster under resource pool',
	'compute_res_pool_manage' : 'Computational resource pool manage',
	'compute_res_sdPool_manage' : 'Storage resource pool manage wizard',
	'compute_res_pool_dcDatil' : 'DC details',
	'compute_res_pool_poolcDatil' : 'Resource pool details',
	'compute_res_pool_pmDetail' : 'Bare Metal details',
	'compute_res_pool_vmDetail' : 'VM details',
	'compute_res_pool_validate' : 'Resource pool name cannot be repeated',
	'compute_res_pool_validate_ename' : 'Resource pool English code cannot be duplicated',
	'compute_res_pool_name_notEmpty' : 'Name cannot be empty',
	'compute_res_pool_platformType_notEmpty' : 'Platform type cannot be empty',
	'compute_res_pool_serviceType_notEmpty' : 'The service type cannot be empty',
	'compute_res_pool_secureAreaType_notEmpty' : 'The security zone cannot be empty',
	'compute_res_pool_availablePartition_notEmpty' : 'The available partition cannot be empty',
	'compute_res_pool_hostType_notEmpty' : 'Resource pool type cannot be empty',
	'compute_res_pool_update' : 'Update resource pool',
	'compute_res_pool_add' : 'Create resource pool',
	'compute_res_pool_delte_tip' : 'There is a CDP, cluster, or bare metal under the current resource pool and cannot be deleted!',
	'compute_res_pool_load_secureAreaTypeErr' : 'Failed to load safe area!',
	'compute_res_pool_load_secureLayerErr' : 'Failed to load the security layer!',
	'compute_res_scanVm' : 'Scan VM',
	'compute_res_ipRule' : 'Ip rule',
	'compute_res_ipRule' : 'Host type',
	'compute_res_scanVm_import' : 'Import',
	'compute_res_scanVm_importAlready' : 'The VM has been imported and cannot be modified',
	'compute_res_scanVm_import_success' : 'Import successfully',
	'compute_res_scanVm_import_tip' : 'Are you sure you want to import this VM?',
	'compute_res_scanVm_import_duTip' : 'Unit is optional',
	'compute_res_scanVm_import_cloudServiceTip' : 'Cloud service is optional',
	'compute_res_scanVm_vm_produce' : 'Production',
	'compute_res_scanVm_vm_management' : 'Manage',
	'compute_res_scanVm_proAndMan_tip' : 'Producing IP and managing IP are not the same',
	'compute_res_scanVm_proAndMan_notEmpty' : 'Managing IP and producing IP cannot be empty at the same time',
	'compute_res_scanVm_ip_tip' : 'Please enter the correct production IP address',
	'compute_res_scanVm_updateDuTip' : 'The deployment unit cannot be modified',
	'compute_res_scanVm_duList' : 'Unit list',
	'compute_res_scanVm_cloudServiceList' : 'List of cloud services',
	'compute_res_virtualswitch' : 'Scan port group',
	'compute_res_virtualswitch_name' : 'Virtual switch',
	'compute_res_virtualswitch_networkTag' : 'Network tag',
	'compute_res_virtualswitch_vlanid' : 'VLANID',
	'compute_res_virtualswitch_tip' : 'Please scan the virtual port group info, and then click the View tab',
	'compute_res_volume_info' : 'Disk info',
	'compute_res_isActiveY' : 'Activated',
	'compute_res_isActiveN' : 'Not active',
	'compute_res_used' : 'Used',
	'compute_res_ename_notEmpty' : 'English code must not be empty',
	'compute_res_load_error' : 'Load failed!',
	'compute_res_number' : 'Serial number',
	'compute_res_selectHost' : 'Select Bare Metal',
	'compute_res_transfer_destHostName' : 'Destination host name',
	'compute_res_transfer_cpu' : 'CPU(core)',
	'compute_res_transfer_remainingCpu' : 'Free CPU(core)',
	'compute_res_transfer_mem' : 'MEM(M)',
	'compute_res_transfer_remainingMem' : 'Free MEM(M)',
	'compute_res_transfer_virtTypeCode' : 'Type',
	'compute_res_transfer_power_lparId' : '"The target lparId cannot be empty!',
	'compute_res_transfer_powerFc' : 'Npiv optical card correspondence cannot be null!',
	'compute_res_transfer_power_scsi' : 'Vscsi optical card correspondence cannot be null!',
	'compute_res_transfer_power_sourceLparID' : 'Source VOIS lparID cannot be empty!',
	'compute_res_transfer_power_destLparID' : 'Target VOIS lparID cannot be empty!',
	'compute_res_transfer_tip' : 'Make sure to manually backup LPAR, and then perform migration operations',
	'compute_res_transfer_toHost' : 'To determine the migration of VM to the bare metal?',
	'compute_res_transfer_success' : 'Migration success!',
	'compute_res_transfer_fail' : 'Migration failure!',
	'compute_res_transfer_inputInfo' : 'Enter migration info',
	'compute_res_pool_releaseTip' : 'There are already devices under the resource pool',
	'compute_res_pool_releaseTip2' : 'If you release the resource, do you physically delete the cluster and the Bare Metal under this pool? Do you confirm the release of the resource?',
	'compute_res_pool_release_success' : 'Release successfully',
	'compute_res_utilization_ratio' : 'Utilization ratio',
	//wmy end
	
	'com_update' : 'Modify',//i18nShow('com_update')
	'com_save' : 'Save',//i18nShow('com_save')
	'com_delete' : 'Delete',//i18nShow('com_delete')
	'com_fail' : 'Fail',//
	'com_remark' : 'Description',//i18nShow('com_remark')
	'com_status' : 'Status',
	'com_select_defalt' : 'Please select',//i18nShow('com_select_defalt')
	'rm_vm_ms_name' : 'Openstack server',
	'rm_vm_ms_ip' : 'Manages Ip',
	'rm_server_name' : 'Server name',
	'com_status_Y' : 'Enabled',
	'com_status_N' : 'Disabled',
	'com_status_N_1' : 'Disabled',
	'com_Y' : 'Yes',
	'com_N' : 'No',
	'com_view_info' : 'Details',
	
	//我的申请
	'my_req_sr_code' : 'Req No',
	'my_req_appName' : 'System',
	'my_req_rm_datacenter' : 'DC',
	'my_req_sr_type' : 'Type',
	'my_req_sr_status' : 'Status',
	'my_req_creator' : 'Req user',
	'my_req_create_time' : 'Req time',//申请时间
	'my_req_summary' : 'General',//概要
	'my_req_operate' : 'Operation',//操作
	'my_req_select' : 'Select',
	'my_req_delete' : 'Delete',
	'my_req_sch' : 'Progress',
	'my_req_cancal_sr' : 'Make sure the service request action is canceled？',
	'my_req_delete_sr' : 'Make sure to close the service request：',
	'my_req_change_sr' : 'After changing the resource request type, the service request of the original system will be deleted,do you sure?',
	'my_req_change_app' : 'After changing the application system, the service request of the original system will be deleted,do you sure?',
	'my_req_change_datacenter' : 'After changing the data center, the application for the original system will be deleted,do you sure?',
	'my_req_service_supply' : 'Cloud service provider',
	'my_req_service_supply_vs' : 'VM supply',
	'my_req_service_supply_ps' : 'Bare Metal supply',
	'my_req_service_expand' : 'Cloud service expansion',
	'my_req_service_expand_ve' : 'VM expansion',
	'my_req_service_recovery' : 'Cloud service recovery',
	'my_req_service_recovery_vs' : 'VM recovery',
	'my_req_service_recovery_ps' : 'Bare Metal recovery',
	'my_req_service_auto' : 'Service Automation',
	'my_req_close_success' : 'Close order successfully，do you want to close this page?',
	'my_req_close_fail' : 'Close order fail,reason：',
	'my_req_close_fail_1' : 'Close order fail',
	'my_req_sr_resubmit' : 'Re-submit',
	'my_req_sr_id_null' : 'SrId is empty!',
	'my_req_sr_memery' : 'MEM',
	'my_req_sr_disk' : 'Disk',
	'my_req_sr_externalDiskSum' : 'External Disk',
	'my_req_sr_rrinfoId' : 'Resource request ID',
	'my_req_sr_duName' : 'Unit ',
	'my_req_sr_du_type' : 'Application deployment unit type',
	'my_req_sr_band_service' : 'Bind cloud services',
	'my_req_sr_band' : 'Bound',
	'my_req_sr_unband' : 'No',
	'my_req_sr_confParameter' : 'Server configuration',
	'my_req_sr_vmName' : 'VM name',
	'my_req_sr_vmuserName' : 'User name',
	'my_req_sr_vmpwd' : 'Password',
	'my_req_sr_para' : 'Parameter',
	'my_req_sr_vmdeviceId' : 'VM ID',
	'my_req_sr_hostId' : 'Host ID',
	'my_req_sr_net' : 'network address',
	'my_req_sr_duId' : 'Unit Id',
	'my_req_sr_deviceType' : 'Device type',
	'my_req_sr_haType' : 'High availability type',
	'my_req_sr_vm_conf' : 'VM configuration',
	'my_req_sr_vm_conf_old' : 'Original service configuration',
	'my_req_sr_vm_conf_new' : 'Create service configuration',
	'my_req_sr_vm_info' : 'VM info',
	'my_req_sr_host_info' : 'Bare Metal info',
	'my_req_sr_plat_form' : 'Platform code',
	'my_req_sr_volume_type' : 'Volume type',
	'my_req_sr_storage' : 'Storage',
	'my_req_sr_input_para' : 'Input parameter',
	'my_req_sr_vm' : 'VM',
	'my_req_sr_mem' : 'MEM',
	'my_req_sr_new_disk' : 'Create disk',
	'my_req_sr_res_pool' : 'Resource pool',
	'my_req_sr_cluster' : 'Cluster',
	'my_req_sr_host' : 'Bare Metal',
	'my_req_sr_LPAR' : 'LPAR prefix',
	'my_req_sr_service_name' : 'Directory',
	'my_req_sr_vm_count' : 'VM number',
	'my_req_sr_chinese' : 'CN abbr：',
	'my_req_sr_english' : 'EN abbr：',
	'my_req_sr_watch' : 'Check',
	'my_req_approve_success' : 'Examination and approval completed,do you want to close this page?',
	'my_req_approve_fail' : 'Approval failure',
	'my_req_approve_error' : 'Approval exception',
	
	'my_req_choose_host' : 'Select Bare Metal',
	'my_req_host_name' : 'Name',
	'my_req_host_res_pool' : 'Resource pool',
	'my_req_det_cluster' : 'Cluster',
	'my_req_det_cpu_used' : 'CPU already used',
	'my_req_det_cpu_free' : 'Free CPU',
	'my_req_det_mem_used' : 'Used MEM(M)',
	'my_req_det_mem_free' : 'Free MEM(M)',
	'my_req_det_select_volume_type' : 'Select volume type',
	'my_req_det_name_and_host' : 'Name / Host',
	'my_req_det_vm_update' : 'Modify VM info',
	'my_req_det_current_host' : 'Current Bare Metal',
	'my_req_det_is_move' : 'Transfer',
	'my_req_det_move_name' : 'moved Bare Metal',
	'tip_my_req_det_select_volume_type' : 'Are you sure you want to select this volume type?',
	'tip_my_req_det_sub' : 'Submit successfully. Do you want to close this page?',
	'tip_my_req_det_select_volume' : 'A device with an unselected volume exists',
	'tip_my_req_det_select_vm' : 'There is a VM that has not been allocated successfully',
	'tip_my_req_det_res_success' : 'Resource allocation success',
	'tip_my_req_det_res_host1' : 'You did not select the Bare Metal and could not complete the modification of the Bare Metal for you!',
	'tip_my_req_det_res_host2' : 'The Bare Metal you selected is consistent with the current migrated Bare Metal!',
	'my_req_det_config' : 'Configure',
	'my_req_det_config_mem' : 'Allocated MEM',
	'my_req_det_config_cpu' : 'Allocated CPU',
	'my_req_det_config_vm' : 'Allocated VM',
	'tip_my_req_det_sub1' : 'Start successfully. Do you want to close this page?',
	'tip_my_req_det_sub2' : 'Accomplish successfully.Do you want to close this page?',
	'my_req_det_view_para' : 'View parameters',
	'my_req_det_para_class' : 'Attribute type',
	'my_req_det_para_need' : 'Required',
	'my_req_det_para_defalt' : 'Default value',
	'my_req_det_para_radio' : 'Option',
	'my_req_det_para_L' : 'Class',
	'my_req_det_para_L1' : 'Parameter classification',
	'my_req_det_para_fill' : 'Please fill in',
	'my_req_det_para_rule' : 'Rule',
	'my_req_det_para_com' : 'Compute',
	'my_req_det_para_net' : 'Network',
	'my_req_det_para_stor' : 'Storage',
	'my_req_deal' : 'Handle',
	'my_req_instance_name' : 'Process instance',
	'my_req_instance_new' : 'Create',
	'my_req_instance_unstart' : 'Not started',
	'my_req_instance_start' : 'Start',
	'tip_my_req_instance_start' : 'The process has started all!',
	'tip_my_req_instance_start_confirm' : 'Sure to start?',
	'tip_my_req_instance_start_fail' : 'Dead start',
	'tip_my_req_instance_msg1' : 'There are no sub processes in the current session. Please re-enter the processing page and try again!',
	'tip_my_req_instance_msg2' : 'This operation cannot be performed at the current child stream!',
	'tip_my_req_type_msg' : 'Cannot add child level under storage!',
	'my_req_type_save' : 'Create request type',
	'my_req_type_update' : 'Modify request type',
	'tip_my_req_type_msg1' : 'There is a child level under the current node!',
	'my_req_du_type' : 'Unit type',
	'my_req_service_list' : 'Cloud service directory',
	'tip_my_req_extend_msg' : 'The currently selected service configuration is consistent with the original service configuration. Please re select!',
	'my_req_recycle_server_conf' : 'Configuration info',
	'my_req_recycle_select_vm' : 'Select the recovery VM',
	'tip_my_req_recycle_select_vm' : 'You do not currently have any devices selected. This operation cannot be performed!',
	'tip_my_req_recycle_msg' : 'The RAC and HA types for the POWER VM need to be recovered together with this deployment unit VM. Is that ok?',
	'tip_my_req_recycle_no_vm' : 'No VM!',
	'tip_my_req_recycle_msg1' : 'The VM to be reclaimed has not been selected at this time. The save operation cannot be performed!',
	'tip_my_req_recycle_msg3' : 'Save the recycle service successfully. Number is',
	'tip_my_req_recycle_msg4' : 'Save expansion service successfully. Number is',
	'tip_my_req_recycle_mutilselect_left':'Only can select one VM，Can not support mutil selects',
	
	//云服务定义
	'cloud_service_name' : 'Name',//服务名称
	'cloud_service_type' : 'Type',//服务类型
	'cloud_service_platform' : 'Platform',//平台类型
	'cloud_service_vm_type' : 'Virtual machine type',//虚机类型
	'cloud_service_ha_type' : 'HA type',//高可用类型
	'cloud_service_host_type' : 'Host type',//主机类型
	'cloud_service_status' : 'Status',//状态
	'cloud_service_operate' : 'Operation',//操作
	'cloud_service_edit' : 'Modify',//修改
	'cloud_service_stop' : 'Disable',//停用
	'cloud_service_start' : 'Enable',//启用
	'cloud_service_stop1' : 'Disable',//禁用
	'cloud_service_status_Y' : 'Enabled',//已启用
	'cloud_service_status_N' : 'Disabled',//已禁用
	'cloud_service_require_N' : 'Non Required',//非必填
	'cloud_service_require_Y' : 'Required',//必填
	'cloud_service_op_model' : 'Operation model',//操作模型
	'cloud_service_delete' : 'Delete',//删除
	'cloud_service_para' : 'Parameter info',//参数信息
	'cloud_service_lead' : 'file export', //文件导出
	'cloud_service_option_defalt' : 'Please select',//请选择
	'cloud_service_info' : 'Information',//云服务信息
	'cloud_service_update' : 'Modify',//修改云服务
	'cloud_service_save' : 'Create cloud services',//新增云服务
	'cloud_service_model' : 'Operation model',//操作模型
	'cloud_service_operate_type' : 'Operation',//操作类型
	'cloud_service_model_flow' : 'Process',//操作流程
	'cloud_service_model_update' : 'Modify',//修改操作模型
	'cloud_service_model_save' : 'Create',//新增操作模型
	'cloud_service_attr_name' : 'Name',//参数名称
	'cloud_service_model_cname' : 'CN name',//参数中文名
	'cloud_service_model_class' : 'Category',//参数类别
	'cloud_service_model_type' : 'Type',//参数类型
	'cloud_service_model_defVal' : 'Default value',//默认值
	'cloud_service_model_isVisible' : 'Visible',//是否可见
	'cloud_service_model_isRequire' : 'Required',//是否必填
	'cloud_service_model_rule' : 'Rules',//填写规则
	'cloud_service_attr_attrKey' : 'Option name',//选项名称
	'cloud_service_attr_attrValue' : 'Option value',//选项值
	'cloud_service_op_info' : 'Property options',//属性选项信息
	'cloud_service_attr_update' : 'Modify parameter',//修改参数信息
	'cloud_service_attr_save' : 'Create parameter',//新增参数信息
	'cloud_service_attr_op_update' : 'Modify parameter',//修改参数选项
	'cloud_service_attr_op_save' : 'Create parameter',//新增参数选项
	//镜像内置软件
	'image_soft_ware_name' : 'Software name',//软件名称
	'image_soft_ware_type' : 'Software type',//软件类型
	'image_soft_ware_path' : 'Path',//存放路径
	'image_soft_ware_remark' : 'Description',//软件描述
	'image_soft_ware_version' : 'Version manage',//版本管理
	'image_soft_ware_version_name' : 'Software version',//软件版本名称
	'image_soft_ware_version_remark' : 'description',//软件版本描述
	'image_soft_ware_version_save' : 'Create software version',//新增软件版本信息
	'image_soft_ware_save' : 'Create software',//新增软件
	'image_soft_ware_update' : 'Update software',//更新软件
	'image_soft_ware_version_view' : 'Software version',//软件版本信息
	'image_soft_ware_version_update' : 'Update software version',//更新软件版本信息
	
	//云服务镜像
	'image_name' : 'Image name',//镜像名称
	'image_size' : 'Size(G)',//镜像大小(G)
	'image_disk_size' : 'capacity(G)',//磁盘容量(G)
	'image_username' : 'Username',//用户名
	'image_remark' : 'Description',//镜像描述
	'image_soft_manage' : 'Software manage',//软件管理
	'image_soft_name' : 'Software',//软件
	'image_soft_version' : 'Version',//版本
	'image_view' : 'View image',//查看镜像
	'image_update' : 'Modify image',//修改镜像
	'image_save' : 'Create image',//新增镜像
	//云服务镜像同步
	'image_synch' : 'Image synch',
	'image_upload' : 'Upload image',
	'image_synch_flow' : 'View process',
	'image_synch_status' : 'Synchronous state',
	'image_synch_status_N' : 'Unsynchronized',
	'image_synch_status_I' : 'In sync',
	'image_synch_status_Y' : 'Synchronized',
	
	//同步入池
	'openstack_synch_com_res_pool' : 'SYNC compute pool',
	'openstack_synch_volume_res_pool' : 'SYNC storage pool',
	'openstack_synch_com_res_pool_confirm' : 'Determines the synchronization calculation resource pool info?',
	'openstack_synch_volume_res_pool_confirm' : 'Determines synchronous storage pool info?',
	'openstack_synch_com_res_pool_succ' : 'Synchronous compute resource pool success!',
	'openstack_synch_volume_res_pool_succ' : 'SYNC storage resource pool success!',
	
	//可用分区
	'available_zone_name' : 'Free partition',
	'available_zone_status' : 'Status',
	'available_zone_save' : 'Create partition',
	'available_zone_update' : 'Modify partition',
	
	//vpc
	'vpc_name' : 'Project name',
	'vpc_save' : 'Create Project',
	'vpc_update' : 'Update Project',
	//外部网络
	'rm_nw_external_net_name' : 'Network name',
	'rm_nw_external_net_type' : 'Network type',
	'rm_nw_external_net_phynet' : 'Physical network',
	'rm_nw_external_net_save' : 'Create external network',
	'rm_nw_external_net_update' : 'Modify external network',
	'rm_nw_external_sub_name' : 'Subnet name',
	'rm_nw_external_sub_ip_version' : 'IP version',
	'rm_nw_external_sub_ip_start' : 'Starting IP',
	'rm_nw_external_sub_ip_end' : 'Cutoff IP',
	'rm_nw_external_sub_mask' : 'Subnet mask',
	'rm_nw_external_sub_gateway' : 'Gateway',
	'rm_nw_external_sub' : 'Subnet manage',
	'rm_nw_external_sub_save' : 'Create subnet',
	'rm_nw_external_sub_update' : 'Modify subnet',
	
	//虚拟路由器
	'rm_nw_router_name' : 'Router name',
	'rm_nw_router_external_net_name' : 'External network name',
	'rm_nw_router_snat_status' : 'SNAT state',
	'rm_nw_router_snat_start' : 'Enable-SNAT',
	'rm_nw_router_snat_stop' : 'Disable-SNAT',
	'rm_nw_router_save' : 'Create virtual router',
	'rm_nw_router_update' : 'Update virtual router',
	
	//存储类型展现
	'openstack_volume_type' : 'Storage volume type',
	'openstack_back_storage_name' : 'Backend storage name',
	'openstack_back_storage_totalCapacity' : 'Total capacity(G)',
	'openstack_back_storage_freeCapacity' : 'Free capacity(G)',
	
	//基础信息维护----------------------------------------------------
	//数据中心
	'rm_datacenter_code' : 'DC code',
	'rm_datacenter_name' : 'DC name',
	'rm_datacenter_ename' : 'Name',
	'rm_datacenter_addr' : 'DC address',
	'rm_datacenter_queueIden' : 'DC identifier',
	'validate_rm_datacenter_length' : 'Length is not greater than 4',
	'validate_rm_datacenter_stringCheck' : 'Must be English or digital',
	'rm_datacenter_save' : 'Create data center',
	'rm_datacenter_update' : 'Update data center',
	'tip_rm_datacenter_use' : 'There is a pool of resources under the current data center and cannot be deleted!',
	'tip_rm_datacenter_use1' : 'Selected name:“',
	'tip_rm_datacenter_use2' : '”,containing resource pool ,can not delete！',
	//路由信息
	'rm_router_datacenter' : 'DC',
	'rm_router_name' : 'Route name',
	'rm_router_defalt_gateway' : 'Gateway',
	'validate_rm_router_ip' : 'The IP address format is incorrect',
	'validate_rm_router_mask' : 'The subnet mask format is incorrect',
	'validate_rm_router_gate' : 'The default gateway format is incorrect',
	'rm_router_update' : 'Modify routing info',
	'rm_router_save' : 'Create routing info',
	//通用服务器管理
	'rm_general_server_name' : 'Name',
	'rm_general_server_type' : 'Type',
	'rm_general_server_mask' : 'Mask',
	'rm_general_server_gateway' : 'Gateway',
	'validate_general_server_ip' : 'The IP address format is incorrect',
	'validate_general_server_mask' : 'The subnet mask format is incorrect',
	'validate_general_server_gate' : 'The gateway format is incorrect',
	'validate_general_server_username' : 'The user name is 30 bits long',
	'validate_general_server_pwd' : 'The password is 50 bits long',
	'rm_general_server_update' : 'Modify server',
	'rm_general_server_save' : 'Create server',
	//平台类型管理
	'rm_platform_code' : 'Code',
	'rm_platform_name' : 'Name',
	'rm_platform_virture_type' : 'Virtual type',
	'validate_rm_platform_code_length' : 'Length is one',
	'rm_platform_save' : 'Create platform type',
	'rm_platform_update' : 'Modify platform type',
	'rm_platform_vm_type_code' : 'Type code',
	'rm_platform_vm_type_name' : 'Type name',
	'validate_rm_platform_virtualTypeCode_length' : 'No more than 2 bits',
	'rm_platform_virture_type_save' : 'Create Virtual type',
	'rm_platform_virture_type_update' : 'Modify Virtual type',
	//安全区域管理
	'rm_nw_secure_name' : 'Name',
	'rm_nw_secure_tier' : 'Security layer manage',
	'rm_nw_secure_update' : 'Modify security zone',
	'rm_nw_secure_save' : 'Create security zone',
	'rm_nw_secure_tier_use' : 'Occupied by security layer',
	'rm_nw_secure_cclass_use' : 'Occupied by section C network',
	'rm_nw_secure_du_use' : 'Occupied by deployed units',
	'rm_nw_secure_pool_use' : 'Occupied by compute pool',
	'rm_nw_secure_tier_name' : 'Security layer',
	'rm_nw_secure_tier_update' : 'Modify security layer',
	'rm_nw_secure_tier_save' : 'Create security layer',
	//网络汇聚管理
	'rm_nw_converge_name' : 'Converge name',
	'rm_nw_converge_update' : 'Update parameters',
	'rm_nw_converge_save' : 'Create network convergence',
	//网络汇聚管理
	'cm_vm_server_manageIp' : 'Host IP',
	'cm_vm_server' : 'Server',
	'cm_vm_server_using' : 'Using',
	'cm_vm_server_update' : 'Modify',
	'cm_vm_server_view' : 'Details',
	'cm_vm_server_save' : 'Create',
	
	//openstack资源展示----------------------------------------------------
	//openstackResShow
	'openstack_res_show_lab' : 'Computational resource pool manage',
	'openstack_res_show_xd_div' : 'Openstack resource pool manage wizard',
	'openstack_res_show_dc_div' : 'DC details',
	'openstack_res_show_volumeListDiv' : 'Volume list',
	'openstack_res_show_volumeSnapListDiv' : 'Snapshot list',
	'openstack_res_show_security_groups_div' : 'Security group',
	'openstack_res_show_virtual_firewall_div' : 'Firewall',
	'openstack_res_show_virtual_firewall_policy_div' : 'Firewall policy',
	'openstack_res_show_virtual_load_balance_div' : 'Load balancing',
	'openstack_res_show_floating_ip_div' : 'Floating IP address',
	'openstack_res_show_active_Y' : 'Activated',
	'openstack_res_show_active_N' : 'Not active',
	//浮动ip
	'openstack_floating_ip_address' : 'IP',
	'openstack_floating_ip_device' : 'Binding device',
	'openstack_floating_ip_pool' : 'Floating IP pool',
	'openstack_floating_ip_band' : 'binding',
	'openstack_floating_ip_unband' : 'Unbundling',
	'validate_openstack_floating_ip_pool_required' : 'The floating IP pool cannot be empty',
	'tip_floating_ip_device_N' : 'Unbound',
	'tip_floating_ip_device_Y' : 'Bound',
	'tip_floating_ip_device_unband_confirm' : 'Are you sure of unbound?',
	'tip_floating_ip_device_band_confirm' : 'Binding?',
	'tip_floating_ip_device_list' : 'VM list',
	'tip_floating_ip_device_name' : 'Device name',
	'tip_floating_ip_device_ip' : 'Device IP',
	'tip_floating_ip_device_app' : 'App system',
	'tip_floating_ip_device_du' : 'Unit ',
	'openstack_floating_ip_save' : 'Create floating IP',
	//负载均衡
	'virtual_lb_name' : 'Name',
	'virtual_lb_subnet' : 'Subnet',
	'virtual_lb_protocol' : 'Protocol',
	'virtual_lb_health' : 'Health examination',
	'virtual_lb_update' : 'Modify load balancing resource pool',
	'virtual_lb_save' : 'Create load balancing resource pool',
	'virtual_lb_vip_save' : 'Create Vip info',
	'virtual_lb_vip_view' : 'Display Vip info',
	'tip_virtual_lb_vip_delete' : 'The current load balancing pool has not been configured with VIP. It cannot be deleted!',
	'virtual_lb_member_name' : 'Device name',
	'virtual_lb_member_ip' : 'Device IP',
	'virtual_lb_member_port' : 'Protocol port number',
	'virtual_lb_member_weight' : 'Weight',
	'virtual_lb_member_view' : 'Member info',
	'validate_lb_member_weight' : 'The weight is between 0-255',
	'virtual_lb_member_save' : 'Create Member',
	'virtual_lb_health' : 'Health check maintenance',
	'validate_lb_health_delay' : 'Delay must be greater than or equal to timeout',
	//卷列表
	'opensatck_volume_name' : 'Name',
	'opensatck_volume_size' : 'Size(G)',
	'opensatck_volume_type' : 'Type',
	'opensatck_volume_storage_type' : 'Storage Type',
	'opensatck_volume_statue' : 'Status',
	'opensatck_volume_is_share' : 'Shared volume',
	'opensatck_volume_no_name' : 'Unnamed',
	'opensatck_volume_no_type' : 'Not specified',
	'opensatck_volume_no_remark' : 'No description',
	'opensatck_volume_device_name' : 'Device name',
	'opensatck_volume_device_status' : 'Device status',
	'opensatck_volume_device_num' : 'Equipment type',
	'opensatck_volume_detail' : 'Volume details',
	'opensatck_volume_snap_create_msg' : 'To create a snapshot success, see the creation results on the disk snapshot page!',
	'opensatck_volume_in_use' : 'The volume is in use and cannot be deleted!',
	'opensatck_snap_name' : 'Snapshot name',
	'opensatck_snap_size' : 'Snapshot size(G)',
	'opensatck_snap_type' : 'Volume ID',
	'opensatck_snap_statue' : 'Snapshot status',
	'opensatck_snap_create_volume' : 'Create disk',
	'opensatck_volume_snap_create_volume_msg' : 'To create successfully, see in the volume list',
	
	//网络资源池
	'rm_network_res_pool' : 'Network Pool',
	'rm_network_res_pool_name' : 'Pool Name',
	'validate_rm_network_res_pool_other' : 'The other is included in the resource pool',
	'validate_end_great_start' : 'The end value must not be less than the starting value',
	'validate_start_beyond' : 'The start must not exceed the range',
	'validate_end_beyond' : 'The deadline must not exceed the limit',
	'validate_rm_network_res_pool_ip_other' : 'The IP section contains other cyber source pool',
	'validate_rm_network_res_pool_ip_c_use' : 'The IP value for this C section already exists. Please enter another value!',
	'rm_network_res_pool_save' : 'Create network resource pool',
	'tip_rm_nw_res_pool_secure_tier_need' : 'Please select a security layer',
	'rm_nw_res_pool_cclass_save' : 'Create C Seg',
	'rm_nw_res_pool_bclass_save' : 'Create B Seg',
	'rm_nw_res_pool_bclass_0' : 'The third and fourth bits of the IP value of the B segment must be 0. Please re-enter it!',
	'rm_nw_res_pool_bclass_use' : 'The IP value for this B section already exists. Please enter another value!',
	'rm_nw_res_pool_mask_check' : 'Please fill in the subnet mask correctly',
	'rm_nw_res_pool_bip_check' : 'Please fill in the B value of section IP correctly!',
	'rm_nw_res_pool_ip_allocedStatus' : 'Distribution state',
	'rm_nw_res_pool_ip_deviceName' : 'Device name',
	'rm_nw_res_pool_ip_updateUser' : 'Operator',
	'rm_nw_res_pool_ip_updateTime' : 'Operating time',
	'rm_nw_res_pool_ip_reUse' : 'Recovery available',
	'rm_nw_res_pool_ip_use' : 'Occupying',
	'tip_ip_use_confirm' : 'Determine whether IP occupies?',
	'rm_nw_res_pool_ip_use_Y' : 'Occupied',
	'rm_nw_res_pool_ip_NA' : 'Free',
	'rm_nw_res_pool_ip_A2DV' : 'Used',
	'rm_nw_res_pool_ip_A2PH' : 'Occupied',
	'tip_ip_use_success' : 'IP occupies success！',
	'tip_ip_use_fail' : 'IP occupies fail！',
	'tip_ip_unuse_confirm' : 'Make sure the recovery is available?',
	'tip_ip_unuse_success' : 'Restore availability success!',
	'tip_ip_unuse_fail' : 'Fail to restore availability!',
	'rm_nw_res_pool_ip_use_by_class' : 'Segment occupancy IP',
	'rm_nw_res_pool_ip_use_by_class_fail0' : 'IP segment placeholder failed!<br/>the following IP has been assigned to the device：<br/>',
	'rm_nw_res_pool_ip_use_by_class_success' : 'IP segment placeholder success!',
	'rm_nw_res_pool_ip_use_by_class_fail' : 'IP segment placeholder failed!',
	'rm_nw_res_pool_ip_use_by_class_msg' : 'Placeholder: this C segment address will be placeholder. This operation cannot be resumed. Please confirm yes,  cancel No.',
	'rm_nw_res_pool_ip_use_by_class_need' : 'With * are required, can not be empty! Please click the "modify" button first, and then fill in the C section after you have filled it in correctly and completely!',
	'rm_nw_res_pool_ip_use_by_class_start' : 'Enable: this C segment address will be enabled. This operation is not recoverable. Confirm, please click Yes, cancel, please click No.',
	'tip_ip_use_start_success' : 'Startup successful!',
	'tip_ip_use_start_fail' : 'Startup failure!',
	'tip_nw_converge_fail' : 'Failed to get the network aggregation list!',
	'tip_nw_platform_fail' : 'Failed to get the platform list!',
	'tip_nw_virtualType_fail' : 'Failed to get the list of virtualized types!',
	'tip_nw_hostType_fail' : 'Failed to get the list of host types!',
	'tip_nw_useType_fail' : 'Failed to obtain list of uses!',
	'tip_nw_secuireAire_fail' : 'Failed to get safe area list!',
	'tip_nw_secureTier_fail' : 'Failed to obtain secure hierarchy list!',
	'tip_nw_secuireAire_need' : 'Please select safe area first!',
	'tip_nw_vmms_need' : 'Please select the VM manage server!',
	'tip_nw_platform_need' : 'Please select platform type first!',
	'tip_nw_virtualType_need' : 'Please select the virtualization type first!',
	'tip_nw_hostType_need' : 'Please select host type first!',
	'rm_nw_res_pool_cclass_update' : 'Modify C Seg',
	'rm_network_res_pool_update' : 'Modify network resource pool',
	'rm_nw_res_pool_bclass_update' : 'Modify  B Seg',
	'rm_nw_res_pool_Utilization_ratio' : 'Utilization ratio',
	'rm_nw_res_pool_ip_total' : 'Total IP',
	'rm_nw_res_pool_ip_free' : 'Free IP',
	'error_rm_nw_res_pool_vpc' : 'Get Project error!',
	'error_rm_nw_res_pool_router' : 'Get router error!',
	'tip_rm_nw_res_pool_add_router' : 'Deciding to join the router network?',
	'tip_rm_nw_res_pool_remove_router' : 'Determining the cancellation of the router network?',
	'tip_rm_nw_res_pool_router1' : 'Cancel router network success!',
	'tip_rm_nw_res_pool_router2' : 'Cancel router network failure!',
	'tip_rm_nw_res_pool_router3' : 'Join router network success!',
	'tip_rm_nw_res_pool_router4' : 'Failed to join router network!',
	
	//流程编排-------------------------------------
	//流程实例
	'instance_name' : 'Name',	
	'instance_type' : 'Type',
	'instance_status' : 'State',
	'instance_srCode' : 'Req No',
	'instance_start_time' : 'Start time',
	'instance_end_time' : 'End time',
	'instance_status0' : 'Create',
	'instance_status1' : 'Running',
	'instance_status2' : 'Pause',
	'instance_status3' : 'Normal end',
	'instance_status4' : 'Force end',
	
	'instance_designer_cursor' : 'Select pointer',
	'instance_designer_direct' : 'Node connection',
	'instance_designer_start' : 'Ingress node',
	'instance_designer_end' : 'End node',
	'instance_designer_decision' : 'Choice',
	'instance_designer_task' : 'Task node',
	'instance_designer_cmd' : 'Command',
	'instance_designer_task' : 'User Task',
	'instance_designer_scr' : 'Script',
	'instance_designer_api' : 'API',
	'instance_designer_fork' : 'Branch',
	'instance_designer_join' : 'Converge',
	'tip_instance_designer_reload' : 'You are editing the template currently selected. Do you want to reload?',
	'tip_instance_designer_retry' : 'Before you have obtained the selected node, refresh retry',
	'tip_instance_designer_error1' : 'Error getting template list',
	'instance_designer_currentModel' : 'Current edit template',
	'instance_flow_save' : 'Create process definition',
	'validate_instance_flow_1' : 'Not more than 50 characters',
	'instance_flow_copy' : 'Replication process template',
	'tip_instance_designer_error2' : 'Select the process template to copy',
	'instance_flow_update' : 'Modify process definition',
	'tip_instance_designer_error3' : 'Select the process template to edit',
	'tip_instance_designer_delete' : 'Determines whether the selected template is deleted?',
	'tip_instance_designer_error4' : 'Select the process template to delete',
	'tip_instance_ending' : 'finish up job',//i18nShow('tip_instance_ending')
	'tip_instance_designer_error5' : 'There is no same name node in the process template',
	'tip_instance_designer_error6' : 'The process template must contain unique start and end nodes',
	'tip_instance_designer_error7' : 'The process template has no nodes',
	
	'tip_instance_fail1' : 'Failed to obtain the flow instance info!',//i18nShow('tip_instance_fail1')
	'tip_instance_fail2' : 'Failed to obtain the flow instance info!',//i18nShow('tip_instance_fail2')
	'tip_instance_fail3' : 'Failed to initialize flow chart info!',//i18nShow('tip_instance_fail3')
	'tip_instance_fail4' : 'There was an error in the pause process!',//i18nShow('tip_instance_fail4')
	'tip_force_over_confirm' : 'Confirm forced termination?',//i18nShow('tip_force_over_confirm')
	'tip_instance_log' : 'Execution log',//i18nShow('tip_instance_log')
	'tip_instance_para' : 'Define global parameters',//i18nShow('tip_instance_para')
	'tip_instance_choose' : 'Choice',//i18nShow('tip_instance_choose')
	'tip_instance_branch' : 'Branch',//i18nShow('tip_instance_branch')
	'tip_instance_converge' : 'Aggregation definition',//i18nShow('tip_instance_converge')
	'tip_instance_assembly' : 'Component container',//i18nShow('tip_instance_assembly')
	'tip_instance_prog' : 'Nested flow',//i18nShow('tip_instance_prog')
	
	//服务策略
	'wf_service_policy' : '',
	'wf_service_policy_name' : 'Strategy name',
	'wf_service_policy_path' : 'Execution path',
	'wf_service_policy_code' : 'Return code',
	'wf_service_policy_method' : 'Execution mode',
	
	//资源分配规则管理-------------------------------------
	//虚机分配参数管理
	'rm_vm_para_objectTypeName' : 'Type',
	'rm_vm_para_objectName' : 'Object',
	'rm_vm_para_paramTypeName' : 'Category',
	'rm_vm_para_value' : 'Values',
	'rm_vm_para_isActive' : 'Effective',
	'rm_vm_para_objectName_global' : 'Global',
	'rm_vm_para_update' : 'Update parameter',
	'tip_rm_vm_para_type_need' : 'Parameter object cannot be null',
	'rm_vm_para_save' : 'Create parameter',
	//虚机分配规则管理
	'rm_vm_rule_softObjectName' : 'Sort object',
	'rm_vm_rule_softTypeName' : 'Sort order',
	'rm_vm_rule_update' : 'Update rule',
	'rm_vm_rule_save' : 'Create rule',
	//IP用途管理
	'ip_use_code' : 'IP use code',
	'ip_use_name' : 'IP use name',
	'ip_use_rel' : 'IP usage relationships',
	'ip_use_update' : 'Update parameter',
	'ip_use_save' : 'Save parameter',
	'ip_use_rel_code' : 'IP usage relationship code',
	'ip_use_rel_save' : 'Create IP usage relationships',
	'ip_use_rel_update' : 'Updating IP usage relationships',
	//IP分配规则管理
	'ip_rule_name' : 'Name',
	'ip_rule_use_type' : 'Use type manage',
	'ip_rule_save' : 'Create IP assignment rule',
	'error_ip_rule_save' : 'Not allowed in two records, host type, platform type, virtualization type, high availability type, and the same!',
	'ip_rule_update' : 'Update IP assignment rule',
	'ip_rule_use_typeName' : 'Type name',
	'ip_rule_use_typeCode' : 'Type relation code',
	'ip_rule_actNum' : 'Valid IP',
	'ip_rule_occNum' : 'Placeholder IP',
	'ip_rule_vmwarePgPefix' : 'Port group prefix',
	'ip_rule_occSite' : 'Occupying position',
	'ip_rule_use_type_save' : 'Create application type',
	'tip_ip_rule_use_type_1' : 'The initial occupation is no greater than the sum of occupation and use',
	'tip_ip_rule_use_type_2' : 'Create success',
	'tip_ip_rule_use_type_3' : 'When the number of bits is 0, there is no need to fill in the placeholder',
	'tip_ip_rule_use_type_4' : 'The number of bits occupied by IP is equal to the number of IP in the placeholder, and can not be added to the placeholder',
	'tip_ip_rule_use_type_5' : 'The number of occupied IP is not equal to the number of IP in the placeholder',
	'ip_rule_use_type_update' : 'Update use type',
	'tip_ip_rule_use_type_6' : 'Please enter the placeholder IP number and the valid IP number first',
	'tip_ip_rule_use_type_7' : 'The starting position and number of seats are not null',
	'tip_ip_rule_use_type_8' : 'The starting position and number of bits must be entered numerically',
	'tip_ip_rule_use_type_9' : 'The starting position and number of seats can not be 0 or negative',
	'tip_ip_rule_use_type_10' : 'Please input IP segment from small to large',
	'tip_ip_rule_use_type_11' : 'The current start IP is already occupying in the previous row!',
	'tip_ip_rule_use_type_12' : 'The position of the placeholder cannot be greater than the sum of the bits and the valid IP',
	
	
	//信息查询-------------------------------------
	//物理机资源查询
	'tip_datacenter_need' : 'Please select the data center first!',
	'tip_app_need' : 'Please select the application system first!',
	'tip_respool_need' : 'Please select the resource pool first!',
	'info_host_controlTime' : 'Control time',
	'info_host_manufacturer' : 'Provider',
	'info_host_model' : 'Model',
	'info_host_info' : 'Bare Metal info',
	//虚拟机资源查询
	'info_vm_beyondHost' : 'Subordinate Bare Metal',
	'info_vm_onlineTime' : 'Uptime',
	'info_vm_info' : 'VM info',
	
	//系统管理----------------------------
	//日志查询
	'sys_log_login_name' : 'Login name',
	'sys_log_model_name' : 'Module name',
	'sys_log_op_type' : 'Operation type',
	'sys_log_op_content' : 'Operation content',
	'sys_log_op_persion' : 'Operator',
	'sys_log_op_time' : 'Operating time',
	//通知管理
	'sys_notice_source' : 'Source',
	'sys_notice_source_auto' : 'automatic recognition',
	'sys_notice_source_man' : 'manual operation',
	'sys_notice_source_vc' : 'VC grab ',
	'sys_notice_type' : 'Type',
	'sys_notice_type_tip' : 'Tip',
	'sys_notice_type_warn' : 'Warning',
	'sys_notice_type_erroe' : 'Error',
	'sys_notice_op_type' : 'type',
	'sys_notice_op_content' : 'Content',
	'sys_notice_op_time' : 'Time',
	'sys_notice_unclosed' : 'Not closed',
	'sys_notice_closed' : 'Closed',
	'sys_notice_close' : 'Close',
	'sys_notice_read':'Read',
	'sys_notice_unread':'UnRead',
	//系统参数配置
	'sys_para' : '',
	'sys_para_name' : 'Parameter name',
	'sys_para_value' : 'Parameter value',
	'sys_para_remark' : 'Description',
	'sys_para_secrit' : 'Encryption',
	'sys_para_update' : 'Modify',
	'sys_para_save' : 'Create',
	//流程参数管理
	'sys_flow' : '',
	'sys_flow_device_name' : 'Device name',
	'sys_flow_instance_name' : 'Instance name',
	'sys_flow_start_time' : 'Start time',
	'sys_flow_add' : 'Create',
	'sys_flow_update' : 'Modify parameters',
	'tip_sys_flow_save' : 'Are you sure you want to save the data?',
	'sys_flow_save' : 'Create process parameter',
	'tip_sys_flow_save1' : 'Are you sure you want to add data?',
	//数据字典类型管理
	'sys_dic_type_name' : 'Name',
	'sys_dic_type_creator' : 'Creater',
	'sys_dic_type_create_time' : 'Creation time',
	'sys_dic_type_editor' : 'Modifier',
	'sys_dic_type_edit_time' : 'Modification time',
	'sys_dic_type_save' : 'Create dictionary type',
	'sys_dic_type_update' : 'Modify dictionary type',
	'tip_sys_dic_type_save' : 'The dictionary type already exists. Please re-enter it!',
	'tip_sys_dic_type_delete' : 'Please empty the dictionary under the dictionary type first!',
	//数据字典管理
	'sys_dic_name' : 'Name',
	'sys_dic_code' : 'Code',
	'sys_dic_order_num' : 'Order',
	'sys_dic_save' : 'Create dictionary',
	'sys_dic_update' : 'Modify dictionary',
	'tip_sys_dic_save' : 'Dictionary code already exists. Please re-enter it!',
	//报表管理
	'sys_report_name' : 'Name',
	'sys_report_remark' : 'Description',
	'sys_report_JASPER_url' : 'JASPER url',
	'sys_report_menu_url' : 'Url',
	'sys_report_update' : 'Modify report',
	'sys_report_save' : 'Create report',
	//角色管理
	'sys_role_name' : 'Name',
	'sys_role_authorization' : 'Authorization',
	'sys_role_view_user' : 'View role user',
	'sys_role_user_list' : 'Users list',
	'sys_role_user_name' : 'Username',
	'sys_role_user_login' : 'Login name',
	'sys_role_user_email' : 'Mailbox',
	'sys_role_user_tel' : 'Telephone',
	'sys_role_update' : 'Modify role',
	'sys_role_detail' : 'Detail',
	'sys_role_save' : 'Create role',
	'tip_sys_role_authorization' : 'Authorization failure!',
	'tip_sys_role_authorization1' : 'Please select a row record for permission settings.',
	'sys_role_authorization_detail' : 'Licensing details',
	'tip_sys_role_authorization_success' : 'Role licensing success.',
	//用户管理
	'sys_user_type' : 'User type',
	'sys_user_org' : 'Organization',
	'sys_user_create_time' : 'Creation time',
	'sys_user_author_role' : 'Role authorization',
	'sys_user_author_service' : 'Cloud service authorization',
	'sys_user_author_app' : 'System authorization',
	'sys_user_author_limit' : 'Set quota',
	'sys_user_select' : 'Selection',
	'tip_sys_user_org' : 'Only one organization is selected!',
	'sys_user_save' : 'Create user',
	'sys_user_update' : 'Modify user',
	'tip_sys_user_need' : 'Please select a user!',
	'tip_sys_user_limit_need' : 'Please select a quota!',
	'tip_sys_user_limit_clear' : 'Are you sure you want to clear the quota for this user?',
	'tip_sys_user_author_role_1' : 'There are roles that have been granted. Please choose again!',
	'tip_sys_user_user_login_2':'The password must be between six and thirty-nine bits, and must contain uppercase letters and numbers!',
	'tip_sys_user_user_login_3':'Please enter the login password again!',
	'tip_sys_role_user_login_1' : 'The login name already exists. Please re-enter it!',
	'sys_user_author' : 'Grant',
	'sys_user_author_N' : 'No',
	'sys_user_author_Y' : 'Yes',
	//组织机构管理
	'sys_org' : 'Organization',
	'sys_org_update' : 'Modify organization',
	'sys_org_save' : 'Create organization',
	'sys_org_save_son' : 'Create sub organization',
	//菜单管理
	'sys_menu_res' : 'Resources',
	'sys_menu_menu' : 'Menu',
	'sys_menu_function' : 'Function',
	'sys_menu_no_pic' : 'No pictures',
	'tip_sys_menu_type' : 'Please select the menu type',
	'sys_menu_update' : 'Modify menu',
	'sys_menu_save' : 'Create menu',
	'sys_menu_save_son' : 'Create submenu',
	
	//数据验证
	'validate_data_required' : 'This item cannot be empty',//i18nShow('validate_data_required')
	'validate_data_remote' : 'Already in use',//i18nShow('validate_data_remote')
	'validate_data_length_4' : 'No more than 4 bits in length',//
	'validate_number' : 'Please enter a number',//i18nShow('validate_number')
	'validate_vmBase_digits' : 'Please enter a digit',//i18nShow('validate_vmBase_digits')
	'validate_vmBase_required' : 'VM base can not be empty',
	'validate_vmBase_min' : 'Must be greater than 0',//i18nShow('validate_vmBase_min')
	'validate_vmBase_max' : 'Not greater than 255',//i18nShow('validate_vmBase_max')
	'validate_num_us' : 'Only inputting English letters, numbers, underlines',
	'validate_app' : 'The business system cannot be empty',
	'validate_datacenter' : 'The data center cannot be empty',
	'validate_sr_type' : 'The apply type cannot be null',
	'validate_sr_time' : 'The application period shall not exceed the end time',
	'validate_service_name_required' : 'Cloud service name cannot be empty',
	'validate_service_name_remote' : 'The service name already exists',
	'validate_serviceStatus' : 'Service status cannot be empty',
	'validate_serviceType' : 'The service type cannot be empty',
	'validate_haType' : 'The high availability type cannot be empty',
	'validate_vmBase_min_1' : 'VM base must be greater than 1',
	'validate_service_platformType' : 'Platform type cannot be empty',
	'validate_service_imageId' : 'System image cannot be empty',
	'validate_service_vmType' : 'The type of VM cannot be empty',
	'validate_service_hostType' : 'The host type cannot be empty',
	'validate_service_systemType' : 'The operating system type cannot be empty',
	'validate_service_storageDataType' : 'Use of data can not be empty',
	'validate_service_model_op_required' : 'The operation type cannot be empty',
	'validate_service_model_op_remote' : 'The operation type already exists',
	'validate_service_model_required' : 'The operation process must not be empty',
	'validate_service_cloudAttrName_required' : 'The parameter name cannot be empty',
	'validate_service_cloudAttrName_remote' : 'The property name already exists',
	'validate_service_attrType_required' : 'Property type cannot be empty',
	'validate_service_attrClass_required' : 'Property class cannot be empty',
	'validate_service_key_required' : 'Option name cannot be empty',
	'validate_service_val_required' : 'Option value cannot be empty',
	
	'validate_image_name_required' : 'Image name cannot be empty',
	'validate_image_name_remote' : 'The image name already exists',
	'validate_image_path_required' : 'Image storage path cannot be empty',
	'validate_image_url_required' : 'Image storage URL cannot be empty',
	'validate_image_url_remote' : 'Image  storage URL already exists',
	'validate_image_number' : 'Please enter a number',
	'validate_image_size_required' : 'Image size cannot be empty',
	'validate_image_size_0' : 'Must be greater than 0',
	'validate_image_disk_required' : 'The disk capacity cannot be empty',
	'validate_image_username_required' : 'User name cannot be empty',
	'validate_image_pwd_required' : 'Password cannot be empty',
	'validate_image_service_required' : 'The server type cannot be empty',
	'validate_image_soft_required' : 'Software cannot be empty',
	'validate_image_soft_version_required' : 'Software version can not be empty',
	'validate_image_soft_remote' : 'Software already exists',
	
	'validate_availableZoneName_required' : 'The partition name may not be empty',
	'validate_availableZoneName_remote' : 'The available partition names cannot be reused',
	'validate_availableZoneName_stringCheck' : 'Can only contain letters, numbers, underscores, and.',//i18nShow('validate_availableZoneName_stringCheck')
	'validate_availableZone_server_required' : 'The server cannot be empty',
	
	'validate_vpc_name_required' : 'The Project name cannot be null',
	'validate_vpc_name_remote' : 'The Project name already exists!',
	'validate_vpc_data_center_required' : 'Please select the data center',
	'validate_vpc_server_required' : 'Please select the server',
	
	'validate_external_net_name_required' : 'The external network name cannot be null',
	'validate_external_net_datacenter_required' : 'Please select the data center',
	'validate_external_net_server_required' : 'Please select the server',
	'validate_external_net_type_required' : 'Please select the network type',
	'validate_external_net_phynet_required' : 'Please select the physical network',
	'validate_external_net_vlanid_required' : 'VLANID cannot be empty',
	'validate_external_sub_name_required' : 'Subnet name cannot be empty',
	'validate_external_sub_ip_version_required' : 'Please select the IP type',
	'validate_external_sub_ip_start_required' : 'The starting IP address cannot be empty',
	'validate_external_sub_ip_end_required' : 'The ending IP address cannot be empty',
	'validate_external_sub_mask_required' : 'Subnet mask cannot be empty',
	'validate_external_sub_gateway_required' : 'The gateway cannot be empty',
	
	'validate_router_name_required' : 'The name of the router cannot be empty',
	'validate_router_external_net_required' : 'Please select an external network',
	'validate_router_external_net_has_sub' : 'No subnet created',
	'validate_router_vpc_required' : 'Please select Project',
	
	
	//showError
	'error_all_requre' : 'Please fill in all the required parameters!',
	'error_select_du' : 'Please select the application deployment unit!',
	'error_select_dev' : 'Please select the application virtual machine!',
	'error_re_select_du' : 'An application for this deployment unit has been submitted. Please re select!',
	'error_re_select_dev' : 'An application for this virtual machine has been submitted. Please re select!',
	'error_select_platform' : 'Please select platform type first!',
	'error_select_one_data' : 'Select at least one data!',
	'error_image_synch' : 'An exception occurs during synchronization!',
	'error_res_pool_use' : 'Occupied by a compute resource pool',
	'error_router_use' : 'Associated virtual router',
	'error_subnet_use' : 'Subnet occupation',
	'error_private_net_use' : 'The current Project has an internal network that is being used',
	'error_private_net_use1' : 'The current router has an internal network that is being used',
	
	//wmy start
	//安全组
	'sg_name' : 'Security group name',//安全组名称
	'sg_rule_manage' : 'Security group rule manage',//安全组规则管理
	'sg_vmRef' : 'Associate VM',//关联虚拟机
	'sg_add' : 'Create security group',//新增安全组
	'sg_modify' : 'Modify security group',//修改安全组信息
	'sg_delete_success' : 'Delete security group successfully',//删除安全组成功
	'sg_delete_fail' : 'Deleting a security group failed',//删除安全组失败
	'validate_sg_name' : 'The security group name cannot be repeated',//安全组名称不能重复
	'validate_sg_name_notEmpty' : 'The security group name cannot be null',//安全组名称不能为空
	'validate_sg_ip' : 'Please fill in the correct IP address',//请填写正确的IP地址
	'validate_sg_tip' : 'The security group contains security rules or is associated with the VM and cannot be deleted',//安全组下含有安全规则或关联了虚拟机，无法删除
	'sg_delete_confirm' : 'Sure delete security group?',//确定删除安全组？
	//安全组规则
	'sg_rule_manage' : 'Security group rule manage',//安全组规则管理
	'sg_rule_direction' : 'App direction',//计量规则应用方向
	'sg_rule_etherType' : 'Protocol type',//协议类型
	'sg_rule_protocol' : 'IP protocol',//IP协议类型
	'sg_rule_remoteIpPrefix' : 'End-to-end IP ',//对端IP地址段
	'sg_rule_portRangeMax' : 'Maximum port',//安全组规则可匹配的最大端口
	'sg_rule_portRangeMin' : 'Minimum port',//安全组规则可匹配的最小端口
	'sg_rule_remark' : 'Description',//
	'sg_rule_operate' : 'Operation',//操作
	'sg_rule_add' : 'Create security group rules',//新增安全组规则
	'sg_rule_save_confirm' : 'Determines the execution of this operation',//确定执行该操作？
	'sg_rule_save_success' : 'Save safe group rules successfully',//保存安全组规则成功
	'sg_rule_save_fail' : 'Failed to save safe group rule',//
	'sg_rule_delete_confirm' : 'Determine delete security group rules?',//确定删除安全组规则？
	'sg_rule_delete_success' : 'Delete successfully',//删除成功
	'sg_rule_delete_fail' : 'Delete failed',//删除失败
	'sg_rule_validate_direction' : 'The application direction of measurement rules can not be empty',//计量规则应用方向不能为空
	'sg_rule_validate_notEmpty' : 'Can not be empty',//不能为空
	'sg_rule_validate_remoteIpPrefix' : 'The end - to - IP address segment cannot be empty',//对端IP地址段不能为空
	'sg_rule_validate_etherType' : 'The protocol type cannot be null',//协议类型不能为空
	'sg_rule_validate_oppositeProject' : 'Please select Project',//请选择Project
	'sg_rule_validate_oppositeSg' : 'The security group cannot be empty',//安全组不能为空
	'sg_rule_validate_number' : 'Please fill in the numbers',//请填写数字
	//防火墙
	'firewall_vfwId' : 'Virtual firewall ID',//虚拟防火墙ID
	'firewall_vfwPolicyId' : 'Virtual firewall policy ID',//虚拟防火墙策略ID
	'firewall_vfwPolicyName' : 'Firewall policy name',//所选防火墙策略名称
	'firewall_vfwName' : 'Firewall name',//防火墙名称
	'firewall_rmark' : 'Description',//备注
	'firewall_operate' : 'Operation',//操作
	'firewall_validate_name' : 'Firewall name cannot be repeated',//防火墙名称不能重复
	'firewall_validate_name_notEmpty' : 'The firewall name cannot be null',//防火墙名称不能为空
	'firewall_validate_name_exist' : 'The firewall name already exists',//防火墙名称已经存在
	'firewall_validate_policy' : 'Firewall policy cannot be empty',//防火墙策略不能为空
	'firewall_validate_route' : 'Routing cannot be null',//路由不能为空
	'firewall_add' : 'Create firewall',//新增防火墙
	'firewall_add_success' : 'Successful operation',//操作成功
	'firewall_add_fail' : 'Operation failed',//操作失败
	'firewall_modify' : 'Modify firewall info',//修改防火墙信息
	'firewall_delete_confirm' : 'Confirm delete firewall?',//确定删除防火墙？
	'firewall_delete_success' : 'Delete firewall success',//删除防火墙成功
	'firewall_delete_fail' : 'Failed to delete firewall',//删除防火墙失败
	'firewall_ref_route' : 'Associate virtual router',//关联虚拟路由器
	//防火墙策略
	'firewall_policy_id' : 'Virtual firewall policy ID',//虚拟防火墙策略ID
	'firewall_policy_name' : 'Firewall policy name',//防火墙策略名称
	'firewall_policy_remark' : 'Description',//备注
	'firewall_policy_operate' : 'Operation',//操作
	'firewall_policy_rule_manage' : 'Firewall policy rule manage',//防火墙策略规则管理
	'firewall_policy_validate_name' : 'Firewall policy name cannot be repeated',//防火墙策略名称不能重复
	'firewall_policy_validate_nameNotEmpty' : 'Firewall policy name cannot be null',//防火墙策略名称不能为空
	'firewall_policy_validate_name_exist' : 'Firewall policy name already exists',//防火墙策略名称已经存在
	'firewall_policy_uesd_tip' : 'Firewall policy is firewall:',//防火墙策略被防火墙：
	'firewall_policy_uesd_tip2' : 'Use cannot be deleted;',//使用，无法删除;
	'firewall_policy_add' : 'Create firewall policy',//新增防火墙策略
	'firewall_policy_modify' : 'Modify firewall policy',//修改防火墙策略
	'firewall_policy_delete_confirm' : 'Determine delete firewall policy?',//确定删除防火墙策略？
	'firewall_policy_delete_tip' : 'Firewall policy contains firewall policy rules and cannot be deleted',//防火墙策略下含有防火墙策略规则，无法删除
	'firewall_policy_delete_success' : 'Delete firewall policy successfully',//删除防火墙策略成功
	'firewall_policy_delete_fail' : 'Failed to delete firewall policy',//删除防火墙策略失败
	//虚拟防火墙策略规则管理
	'vfw_policyRule_manage' : 'Firewall policy rule manage',//防火墙策略规则管理
	'vfw_policyRule_vfwPolicyRuleId' : 'Virtual firewall policy rules ID',//虚拟防火墙策略规则ID
	'vfw_policyRule_vfwPolicyId' : 'Virtual firewall policy ID',//虚拟防火墙策略ID
	'vfw_policyRule_vfwPolicyRuleName' : 'Virtual firewall policy name',//虚拟防火墙策略名称
	'vfw_policyRule_remark' : 'Description',//描述
	'vfw_policyRule_isShare' : 'Share',//是否共享
	'vfw_policyRule_shareY' : 'Yes',//共享 
	'vfw_policyRule_shareN' : 'No',//非共享
	'vfw_policyRule_protocolType' : 'Protocol type',//协议类型
	'vfw_policyRule_ipVersion' : 'Protocol version',//协议版本
	'vfw_policyRule_sourceIpAddress' : 'Source address',//源地址
	'vfw_policyRule_destIpAddress' : 'Destination address',//目标地址
	'vfw_policyRule_sourcePort' : 'Source port',//源端口
	'vfw_policyRule_descPort' : 'Destination port',//目标端口
	'vfw_policyRule_ruleAction' : 'The actions that are performed when the traffic matches a rule',//流量匹配规则时所执行的动作
	'vfw_policyRule_enabled' : 'Rule validity',//规则的有效性
	'vfw_policyRule_operate' : 'Operation',//操作
	'vfw_policyRule_Y' : 'Yes',
	'vfw_policyRule_N' : 'No',
	'vfw_policyRule_validate_name' : 'Firewall policy name cannot be repeated',//防火墙策略规则名称不能重复
	'vfw_policyRule_name_exist' : 'Firewall policy rule name already exists',//防火墙策略规则名称已经存在
	'vfw_policyRule_validate_port' : 'Please fill in the correct port number',//请填写正确的端口号
	'vfw_policyRule_notEmpty' : 'Can not be empty',//不能为空
	'vfw_policyRule_validate_ip' : 'The IP format is incorrect',//ip格式不正确
	'vfw_policyRule_validate_number' : 'Please enter a number or interval',//请输入数字或区间
	'vfw_policyRule_required_number' : 'Please fill in the numbers',//请填写数字
	'vfw_policyRule_add' : 'Create firewall policy rules',//新增防火墙策略规则
	'vfw_policyRule_modify' : 'Modify firewall policy rules',//修改防火墙策略规则
	'vfw_policyRule_delete_confirm' : 'Determine delete firewall policy rules?',//确定删除防火墙策略规则？
	'vfw_policyRule_delete_success' : 'Delete successfully',//删除成功
	'vfw_policyRule_delete_fail' : 'Delete failed',//删除失败
	//服务申请
	'my_req_fillIn':'Please Fill in',
	'my_req_server_number':'Number',
	'my_req_device_stage':'Platform',
	'my_req_device_attrDatil':'Detail',
	'my_req_delete_confirm':'Confirm delete',
	'my_req_select_cloudServices':'Please select the service directory!',
	'my_req_cpuNum':'Cpu(core)',
	'my_req_memNum':'MEM(M)',
	'my_req_sysDisk':'System disk(G)',
	'my_req_nextStep':'Next step',
	'my_req_yes':'Determine',
	'my_req_selectCpu_tip':'The application for CPU is overrun, and you can apply for the CPU kernel at present:',
	'my_req_selectMem_tip':'The memory is out of bounds. You can apply for memory at present:',
	'my_req_system_error':'System error',
	'my_req_vmNum':'VM number',
	'my_req_fillIn_rule':'Fill in the rules',
	'my_req_parameter_class':'Parameter classification',
	'my_req_compute_res':'Compute',
	'my_req_network':'Network',
	'my_req_storage_res':'Storage',
	'my_req_selectList':'Select',
	'my_req_valueParam':'Option',
	'my_req_select_parameter':'Select parameters',
	'my_req_finllIn_update':'Fill in / modify parameters',
	'my_req_validate_tip':':Required input numeric format!',
	'my_req_finllIn_du':'Please add deployment units',
	'my_req_save_successTip':'Save the service request successfully. Number as：',
	'my_req_save_failTip':'Save failed, return info：',
	'my_req_submit_successTip':'Submit successfully!',
	'my_req_submit_failTip':'Submission failed to return info:',
	'my_req_auto_addInfo':'Please add application info',
	'my_req_auto_selectCs':'Please select the cloud service!',
	'my_req_auto_selectVm':'Select VM',
	'my_req_auto_cloudServices_name':'Service name',
	'my_req_auto_cpu':'cpu=',
	'my_req_auto_mem':'MEM=',
	'my_req_auto_disk':'M System disk=',
	'my_req_auto_g':'G',
	'my_req_auto_ip':'Production IP：, ',
	//wmy end
	'tip_error' : 'Error occurred',//i18nShow('tip_error')
	'tip_save_success' : 'Save successfully!',//i18nShow('tip_save_success')
	'tip_save_fail' : 'Save failed!',//i18nShow('tip_save_fail')
	'tip_delete_confirm' : 'Are you sure you want to delete?',//i18nShow('tip_delete_confirm')
	'tip_delete_success' : 'Delete successfully!',//i18nShow('tip_delete_success')
	'tip_delete_fail' : 'Delete failed',//i18nShow('tip_delete_fail')
	'tip_choose_image' : 'Please select mirror image!',
	'tip_re_choose_image' : 'There is a mirror in sync or synchronization. Please select again!',
	'tip_create_flow_success' : 'Create process successfully',
	'tip_choose_vmms' : 'Please select the VM manage server!',
	'tip_external_vpc_error' : 'According to the external network access to Project error!',
	'tip_req_fail' : 'request was aborted',//i18nShow('tip_req_fail')
	'tip_op_success' : 'Successful operation',//i18nShow('tip_op_success')
	'tip_all_required' : 'With * are required, can not be empty!',
	'tip_paramValue_isNum' : 'A system parameter that can only be encrypted with a parameter value!',
	'tip_delete_catalog' : 'Cannot delete a directory with submodules!',

	'sys_l_report_conKey':'Keyword',
	'sys_l_report_con_describe':'Description',
	'sys_l_report_SQL_sentence':'SQL',
	'sys_l_report_sql_need':'SQL required',
	'sys_l_report_delete_condition':'Delete',
	'sys_l_report_condition':'Condition',
	'sys_report_sqlKey':'SQL keyword',
	'sys_report_sql_sentence':'SQL sentence ',
	'sys_report_attr_keyword':'Property keyword：',
	'sys_report_condition_keyword':'Conditional keyword：',
	'sys_report_selectOption':'Please check the options that you want to appear：',
	'sys_report_year':'Year',
	'sys_report_month':'Month',
	'sys_report_day':'Day',
	'sys_report_attribute_describe':'Description',
	'sys_report_attribute_add':'Create attr',
	'sys_report_attribute_delete':'Delete attr',
	'sys_report_sql_delete':'Delete SQL',
	'sys_report_time_tip':'Please tick at least one date form!',
	'sys_report_save_confirm':'Confirm saving report info?',
	
	
	
	
	
	
	'sys_excel_validateIp':'Please fill in the request correctly IP!',
	'sys_excel_fileName':'File name',
	'sys_excel_createTime':'Generation time',
	'sys_excel_modifyTime':'Modification time',
	'sys_excel_writeDb':'Whether write to database',
	'sys_excel_writeDb_confirm':'Make sure to write to the database',
	'sys_excel_error':'IP or account password incorrect, did not generate EXCEL!',
	'sys_excel_file_import':'File',
	'sys_excel_validate_ip':'IP cannot be empty',
	'sys_excel_validate_username':'User name cannot be empty',
	'sys_excel_validate_password':'Password cannot be empty',
	'sys_excel_export':'Export',
	'sys_excel_write_database':'Write database',
	'sys_selfTest_status_normal':'Normal',
	'sys_selfTest_status_abnormal':'Abnormal',
	'sys_selfTest_testing':'Testing...',
	'sys_selfTest_test':'Test',
	'sys_selfTest_confirm':'Determine the status of the device detected',
	'sys_selfTest_hmcList':'HMC list',
	'sys_selfTest_hmc_message':'HMC detection message:',
	'sys_selfTest_hmc_fail':'HMC test failed!',
	'sys_selfTest_vcList':'VCENTER list',
	'sys_selfTest_vc_message':'VC detection message:',
	'sys_selfTest_vc_fail':'VC test failed!',
	'sys_selfTest_imageList':'Mirrored list',
	'sys_selfTest_image_message':'Mirror detection message:',
	'sys_selfTest_image_fail':'Failed mirror detection!',
	'sys_selfTest_scriptList':'Script server list',
	'sys_selfTest_script_message':'Script check message:',
	'sys_selfTest_script_fail':'Script check failed!',
	'sys_selfTest_autoList':'AUTOMATION list',
	'sys_selfTest_auto_message':'automation detection message:',
	'sys_selfTest_auto_fail':'automation test failed!',
	'sys_selfTest_mqList':'MQ server list',
	'sys_selfTest_mq_message':'MQ detection message:',
	'sys_selfTest_mq_fail':'Test failed!',
	'sys_selfTest_bpm_message':'BPM detection message:',
	'sys_selfTest_bpm_fail':'BPM test failed!',

	//登录
	'login_name' : 'Login name',//i18nShow('login_name')
	'login_val_code' : 'Validate Code',//i18nShow('login_val_code')
	'login_exist' : 'Are you sure you want to quit the system?',//i18nShow('login_exist')
	
	//我的控制台
	'my_console_total_mem' : 'Total MEM',//i18nShow('my_console_total_mem')
	'my_console_free_mem' : 'Free MEM',//i18nShow('my_console_free_mem')
	'my_console_total_CPU' : 'Total CPU',//i18nShow('my_console_total_CPU')
	'my_console_free_CPU' : 'Free CPU',//i18nShow('my_console_free_CPU')
	'my_console_no_data' : 'No data',//i18nShow('my_console_no_data')
	'unit_he' : 'nuclei',
	//app
	'val_l_app_length_100' : 'At most not more than 100 characters',//i18nShow('val_l_app_length_100')
	'val_l_app_length_32' : 'At most not more than 32 characters',//i18nShow('val_l_app_length_32')
	'val_l_app_length_3-6' : 'English abbreviation for 3~6 characters',//i18nShow('val_l_app_length_3-6')
	'val_l_app_letter_num' : 'Lower case letters, numbers',//i18nShow('val_l_app_letter_num')
	'val_l_app_letter_num1' : 'English letters and numbers',//i18nShow('val_l_app_letter_num1')
	'val_l_app_remark_long' : 'Remark is too long',//i18nShow('val_l_app_remark_long')
	'tip_l_app_save' : 'There are deployment units under the subsystem, and no subsystems can be added!',//i18nShow('tip_l_app_save')
	'l_app_save' : 'Create application subsystem',//i18nShow('l_app_save')
	'tip_l_app_du_save' : 'There is a subsystem under this system and no new deployment units can be added!',//i18nShow('tip_l_app_du_save')
	'l_app_du_save' : 'Create deployment unit',//i18nShow('l_app_du_save')
	'l_app_update' : 'Maintenance application subsystem',//i18nShow('l_app_update')
	'tip_l_app_delete' : 'Are you sure you want to delete the application system (subsystem)?',//i18nShow('tip_l_app_delete')
	'tip_l_app_delete_msg' : 'There are deployment units under the system (subsystem) and are not allowed to be deleted!',//i18nShow('tip_l_app_delete_msg')
	'tip_l_app_delete_msg1' : 'Is there a subsystem under the system (or subsystem)?!',//i18nShow('tip_l_app_delete_msg1')
	'l_app_du_update' : 'Maintenance deployment unit',//i18nShow('l_app_du_update')
	'l_app_max' : 'Max',//i18nShow('l_app_max')
	'l_app_min' : 'Min',//i18nShow('l_app_min')
	'l_app_average' : 'Average',//i18nShow('l_app_average')
	
	//存储资源池
	'storage_res_pool_PLATINUM' : 'Pt',
	'storage_res_pool_GOLD' : 'Gold',
	'storage_res_pool_SILVER' : 'Silver',
	'tip_storage_res_pool_name_same' : 'The storage resource pool name cannot be repeated with its resource pool name',
	'storage_res_pool_device_name' : 'Name',
	'storage_res_pool_device_model' : 'Model',
	'storage_res_pool_device_fac' : 'Provider',
	'storage_res_pool_location_code' : 'Location',
	'storage_res_pool_ip' : 'Manage IP',
	'storage_res_pool_order' : 'Order',
	'storage_res_pool_name' : 'Pool Name',
	'storage_res_pool_path' : 'Path',
	'storage_res_pool_save' : 'Create storage resource pool',
	'storage_res_pool_update' : 'Modify storage pool',
	'tip_storage_res_pool_delete' : 'The resource pool contains a child pool or associated device and cannot be deleted!',
	'storage_res_pool_son_save' : 'Create storage resource pool',
	'storage_res_pool_son_update' : 'Modify storage resource pool',
	'res_device_order' :'Order',
	'res_device_bareState' :'Bare Metal',
	'res_device_isInvc_state' :'VM-M',
	'res_device_unmatched' :'Unmatched',
	'res_device_matched' :'Matched',
	'res_device_mached_noinvc' :'Matched nanotube',
	'res_device_snValidate' :'SN cannot repeat',
	'res_device_saetValidate' :'Location number cannot be repeated',
	'res_device_deviceName_validate' :'Device name cannot be repeated',
	'res_device_stringCheck' :'Only English letters underline and minus',
	'res_device_manageIp_validate' :'Managing Ip cannot be repeated',
	'res_device_manageIp_notEmpty' :'Managing IP cannot be null',
	'res_device_validate_sn' :'SN cannot be empty',
	'res_device_validate_seat' :'Positional code cannot be null',
	'res_device_validate_type' :'Device type cannot be empty',
	'res_device_validate_manufacturer' :'Provider cannot be empty',
	'res_device_validate_deviceModelId2' :'Device model cannot be empty',
	'res_device_validate_isBare' :'Please choose whether or not to bare metal',
	'res_device_validate_cpu' :'CPU size cannot be empty',
	'res_device_validate_mem' :'MEM cannot be empty',
	'res_device_validate_mustBeNumbers' :'Must be numbers',
	'res_device_validate_style' :'Type cannot be empty',
	'res_device_validate_disk' :'The disk cannot be empty',
	'res_device_validate_diskName' :'Disk name cannot be empty',
	'res_device_update_title' :'Modify device info',
	'res_device_validate_ISCSI' :'When the storage type is ISCSI, storage, or NAS storage, the managed IP cannot be empty!',
	'res_device_validate_deviceName' :'The device name cannot be null when the device type is stored!',
	'res_device_add_title' :'Create device info',
	'res_device_datastore_name' :'Datastore name',
	'res_device_datastore_path' :'Datastore path',
	'res_device_datastore_order' :'Datastore order',
	'res_device_datastore_identifier' :'Identifier',
	'res_device_datastore_valiName' :'The datastore name cannot be repeated',
	'res_device_datastore_valiPath' :'The datastore path cannot be repeated',
	'res_device_datastore_valiOrder' :'Serial number cannot be null',
	'res_device_datastore_valiSan' :'When the storage type is SAN, the identifier cannot be null!',
	'res_device_datastore_valiNas' :'When the storage type is ISCSI, storage, or NAS storage, the path cannot be empty!',
	'res_device_datastore_add' :' Create datastore',
	'res_device_update_param' :'Modify parameters',
	'res_device_delete_datastore_success' :'Delete datastore successfully',
	'res_device_delete_datastore_fail' :'Delete datastore fail',
	'bpmlist':'BPM Server',
	'wf1':'You are editing the process [ ',
	'wf2':' ] Make sure to switch to',
	'wf3':'Save success!',
	'wf4':'Please select process choreographer',
	'wf5':'No processes are currently edited!',
	'wf6':'Release process success!',
	'wf7':'Release failure!',
	'wf8':'No processes are currently edited!',
	'wf9':'The original template will be covered to make sure the new template is imported',
	'wf10':'Please first create or select process definitions and then add node!',
	'wf11':'The added node does not conform to the unique rule!',
	'wf12':'Drag onto the canvas to create the node',
	'wf13':'Start',
	'wf14':'End',
	'wf15':'Select',
	'wf16':'Fork',
	'wf17':'Polymerization',
	'wf18':'Container',
	'wf19':'Sub-process',
	'wf20':'Save failed!',
	'process_mustSelect_script':'Script only selected...',
	'process_select_script':'Select script',
	'tip_instance_route':'Route definition',
	'process__Nested_flow':'Nested flow',
	'process_container':'Container',
	'process_polymerization':'Polymerization',
	'process_branch':'Branch',
	'process_js_select':'Choice',
	'process_start':'Start',
	'process_end':'End',
	'message1':'The resource pool contain devices and can not be deleted',
	'message2':'Delete storage resource pool success!',
	'message3':'Fail to delete storage resource pool!',
	'message4':'Are you sure of deleting',
	'message5':'Are you sure of cancel the relation ?',
	'message6':'Cancel sucess',
	'message7':'Cancel fail',
	'message8':'Storage Device',
	'message9':'choose one at least',
	'message10':'Are you sure the relevance?',
	'k1':'Name',
	'k2':'Provider',
	'k3':'Model',
	'k4':'Capacity',
	'suii1':'Nodename:',
	'suii2':'Policy:',
	'suii3':'Timeout:',
	'suii4':'Type:',
	'suii5':'Handle:',
	'suii6':'Execute auto:',
	'suii7':'Start time:',
	'suii8':'Return code:',
	'suii9':'End time:',
	'suii10':'Last time:',
	'suii11':'Path:',
	'suii12':'Result:',
	'suii13':'Instance information is none,please try again',
	'suii14':'+History',
	'suii15':'-History',
	'suii16':'+Service Parameter',
	'suii17':'-Serivce Parameter',
	'suii18':'Service parameter',
	'device_type_code':'typeCode',
	'suii19':'min',
	'suii20':'s',
	'stu1':'Complete Normal',
	'stu2':'Complete Abnormal',
	'stu3':'Pause Normal',
	'stu4':'Pause Abnomal',
	'stu5':'Running',
	'stu6':'Not Execution',
	'stu7':'Execution Timeout',
	'con1':'Build',
	'con2':'Running',
	'con3':'Pause',
	'con4':'End Normal',
	'con5':'End Abnormal',
	'kea1':'Para Define',
	'kea2':'Router branch Define',
	'kea3':'Router Select Define',
	'qa1':'Start',
	'qa2':'End',
	'qa3':'Select',
	'qa4':'Branch',
	'qa5':'Converge',
	'qa6':'Commond',
	'qa7':'Label',
	'qa8':'Script',
	'qa9':'API',
	'zh1':'Import process template',
	'zh2':'Create script param',
	'zh3':'Export process template',
	'zh4':'Save successfully!',
	'zh5':'Please select...',
	'zh6':'Save failed!',
	'match1':'Make sure matching?',
	'match2':'Match failed!',
	'deletesuc':'Delete successfully!',
	'more':'More',
	'paraname':'Please input param key',
	'paravalue':'Please input param value',
	'maxline1':'Maximum length 100',
	'maxline2':'Maximum length 200',
	'deleteline1':'Make sure to delete this line?',
	'deleteline2':'Delete',
	'delroute':'Delete route',
	'input_route':'Please input router name',
	'input_exp':'Please input expression',
	'maxline3':'Maximum length 50',
	'input_node_name':'Please input node name',
	'maxline4':'Maximum length 50',
	'node_notice':'Commas cannot be included in the node name',
	'input_outtime':'Please input the Timeout(integer)',
	'input_integer':'please input an integer',
	'select_expect':'Please select the Exception handling',
	'select_script':'Please select the Script',
	'select_service':'Please select the Service strategy',
	'requiredURL':'Please enter the URL',
	'selectgroup':'Please select Working_group',
	'query_vm_m':'VM-M',
	'res_device_sn':'SN',
	//tenant
	'tenantManage_jsp_tenantName':'tenantName',
	'tenantManage_jsp_createUser':'createUser',
	'tenantManage_jsp_createTime':'createTime',
	'tenantManage_jsp_remark':'remark',
	'tenantManage_option_auto':'userAutho',
	'resource_pool_authorization':'resourcePoolAuthorization',
	'tenantManage_option_quota':'quotaManage',
	'tenantManage_jsp_add':'add',
	'tenantManage_jsp_update':'update',
	'tenantManage_option_update':'update',
	'tenantManage_option_delete':'delete',
	'tenantName_submit_not_null':'tenant name can\'t be  null',
	'tenantManage_option_delete_failed':'can\'t delete',
	'tenantManage_option_delete_confirm':'are you sure to delete？',
	//tenant-autho
	'autho_jsp_loginName':'loginName',
	'autho_jsp_roleName':'roleName',
	'autho_jsp_roleName_manager':'manager',
	'autho_jsp_roleName_customer':'customer',
	'autho_jsp_userId':'userId',
	'autho_jsp_autho_tip':'continue？',
	'autho_jsp_autho_res_tip':'continue？',
	'autho_jsp_autho_res_name':'pool name',
	'quota_not_complete':'not complete',
	'quota_invalid_long':'invalid number。。',
	'quota_invalid':'invalid number',
	'tenantManage_quota_show_createProject':'please config project first',
	'tenantManage_quota_show_createO':'please config openstack first',
	'tenantManage_quota_show_createP':'please config power first',
	'tenantManage_quota_show_createX':'please config x86 first',
	'tenantManage_quota_projectList':'project list',
	'project_name':"ProjectName",
	'com_l_ipAddr':'IP',
	'vlb_tip_name_not_null':"name can can't null",
	'vlb_tip_protocalPort_not_null':"protocalPort can't be null",
	'vlb_tip_protocal_not_null':"protocal can't be null",
	'vlb_tip_protocalPort_invalid':"protocalPort must be number",
	'vlb_tip_subnet_not_null':"subnet can't be null",
	'vlb_tip_ip_not_null':"ip can't  be null",
	//volume
	'my_req_service_auto_addVolume':"Add volume",
	'deviceIp':"VM IP",
	'update_logininfo':"update login info",
	'my_req_diskCapacity':'diskCapacity',
	
// 在上面添加国际化配置
		'temp' : '',
		
	//修改密码
	'sys_l_ModifySaveSuccess':'Save success!',
	'sys_l_ModifyOldPwdWrong':'The original password is wrong!',
	'sys_l_ModifyPasswordEnpty':'The input password is empty!',
	'sys_l_ModifyPasswordIncorrect':'The password you entered two times is incorrect!'	
};