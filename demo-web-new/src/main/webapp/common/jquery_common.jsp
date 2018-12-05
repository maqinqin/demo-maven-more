<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="${ctx}/jquery/js/jquery-ui.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery/css/ui.jqgrid.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery/css/all_base.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery/css/jquery-ui-1.8.1.custom.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery/css/jbox.css"></link>

<script type="text/javascript" src="${ctx}/scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/grid.locale-zh_CN.js"></script>
<%-- <script type="text/javascript" src="${ctx}/jquery/js/grid.locale-en_US.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/grid.locale-en_US.js"></script> --%>
<script type="text/javascript" src="${ctx}/jquery/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/common.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery.jbox.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery_global.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.open-layer.js"></script>

<!--
<script type="text/javascript" src="${ctx}/jquery/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/grid.locale-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery.jqGrid.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery/js/autocomplete/jquery.autocomplete.styles.css"></link>
<script type="text/javascript" src="${ctx}/jquery/js/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery.ui.dialog.js"></script> 
<script type="text/javascript" src="${ctx}/jquery/js/grid.custom.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery.tablednd.js"></script>

<div id="windownbg" class="windownbg">
</div>
<div id="windown-box1" class="windown-box" style="left: 50%; top: 50%; margin-top: -161.5px;
    margin-left: -266px; z-index: 999999; display: none; background-color: #add2da;cursor: pointer;" onclick="closeTip()">
    <div id="windown-title1" style="width: 510px; display: none;" class="windown-title">
        <h2>
        </h2>
        <span id="windown-close1" class="windown-close">关闭</span></div>
    <div id="windown-content-border1" class="windown-content-border">
        <div id="windown-content1" style="width: 500px; height: 270px;">
            <label id="tipInfo1" style="margin-left:6px;font-family:宋体;font-size:14px;color:black;"></label>
        </div>
    </div>
</div>

<div id="windown-box2" class="windown-box" style="width: 250px; height: 130px; left: 50%;
        top: 50%; margin-top: -161.5px; margin-left: -266px; z-index: 999999; display: none;">
        <div id="windown-title2" class="windown-title">
            <label>
                </label>
            <span id="windown-close2" class="windown-close" onclick="closeTip()">关闭</span>
        </div>
        <div id="windown-content-border2" class="windown-content-border">
            <div id="windown-content2" style="width: 250px; height: 120px;">
                <div style="text-align: center;">
                    <input type="hidden" id="hid_container" name="hid_container" value="" />
                    <br />
                    <div id="tipInfo2" style="font-size:14px;">
                        </div>
                   <div class="btnbar" style="margin-left: 25px;">
	                    <input type="button" class="btn" value="确定" id="btn_sure" />
	                    <input type="button" class="btn" value="取消" id="btn_cancel" onclick="closeTip()" />
                    </div>
                </div>
            </div>
        </div>
</div>

<div id="windown-box3" class="windown-box" style="width: 250px; height: 130px; left: 100.5%;
        top:106.3%;background-color:#b3e2fc;border:solid 1px #A6CACA;margin-top: -161.5px; margin-left: -266px; z-index: 999999; display: none;border-radius: 0px;box-shadow: #666 0px 0px 0px;">
        <div id="windown-content-border3" class="windown-content-border">
            <div id="windown-content3" style="width: 250px; height: 120px;text-align: center;">
                    <div id="tipInfo3" style="font-size:14px;color:black;margin-top: 40px;">
                        </div>
                        <div class="btnbar" style="margin-left: 25px;">
	                    <input type="button" class="btn" value="关闭" onclick="closeAlertBox()" />
                    </div>
            </div>
        </div>
</div>

<div id="windown-box4" class="windown-box" style="width: 250px; height: 130px; left: 50%;
        top: 50%; margin-top: -161.5px; margin-left: -266px; z-index: 999999; display: none;">
        <div id="windown-title4" class="windown-title" style="background-color: red">
            <label>
                </label>
            <span id="windown-close4" class="windown-close" onclick="closeTip()">关闭</span>
        </div>
        <div id="windown-content-border4" class="windown-content-border">
            <div id="windown-content4" style="width: 250px; height: 120px;">
                <div style="text-align: center;">
                    <input type="hidden" id="hid_error" name="hid_container" value="" />
                    <br />
                    <div id="tipInfo4" style="font-size:14px;">
                        </div>
                   <div class="btnbar" style="margin-left: 25px;">
	                    <input type="button" class="btn" value="关闭" onclick="closeTip()" />
                    </div>
                </div>
            </div>
        </div>
</div> -->
 