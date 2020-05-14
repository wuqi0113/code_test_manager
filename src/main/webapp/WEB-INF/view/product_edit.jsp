<%@ page language="java" import="java.util.*,com.chainfuture.code.common.WwSystem" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getServletContext().getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>后台管理</title>
    <link rel="stylesheet" href="<%=path%>/public/layui-master/src/css/layui.css">
    <link rel="stylesheet" href="<%=path%>/public/css/main.css">
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/jquery.form.js"></script>
    <script type="text/javascript" src="<%=path%>/js/commons/package.js"></script>
    <base href="<%=WwSystem.getRoot(request)%>">
</head>
<body class="layui-layout-body">
<div class="layui-layout">
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <div>
                <h2 class="re-title">生产管理 > 产品详情 </h2>
                <div style="height: 1px;background-color: black;"></div>
            </div>
            <div class="re-form">
                <h5>产品信息</h5>

                <form class="layui-form layui-form-pane1 re-formp" action="<%=path%>/product/addPro" lay-filter="first" onsubmit="check()" enctype="multipart/form-data">
                    <input type="hidden" value="${data.id}" name="id" id="id">
                    <input type="hidden" value="999999" name="assetId" id="assetId">
                    <div class="layui-form-item" style="display: none">
                        <label class="layui-form-label">所属地址：</label>
                        <div class="layui-form-mid layui-word-aux">
                            <input type="text" name="primeAddr"  id="primeAddr" value="1ojviBeqSqasEKRWqifwLwwP5MzJN181y" class="layui-input">
                        </div>

                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">产品名称：</label>
                        <div class="layui-form-mid layui-word-aux">
                            <input type="text" name="proName" id="proName" value="${data.proName}" lay-verify="required|proName" required placeholder="" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">英文名称：</label>
                        <div class="layui-form-mid layui-word-aux">
                            <c:if test="${data.sname!=null}">
                                <input type="text" name="sname" id="sname" value="${data.sname}" readonly="readonly" class="layui-input">
                            </c:if>
                            <c:if test="${data.sname==null}">
                                <input type="text" name="sname" id="sname" value="" lay-verify="required|sname" required placeholder="" autocomplete="off" class="layui-input">
                            </c:if>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">产品描述：</label>
                        <div class="layui-input-block">
                            <textarea name="proDescribe" id="proDescribe" placeholder="" class="layui-textarea">${data.proDescribe}</textarea>
                        </div>
                    </div>


                    <div class="layui-form-item">
                        <label class="layui-form-label">产品广告图：</label>
                        <div class="layui-input-block">
                            <c:if test="${data.id!=null}">
                                <input type="text" id="proAdvertImg" name="proAdvertImg" readonly="readonly"  value="${data.proAdvertImg.substring(data.proAdvertImg.lastIndexOf("/")+1)}"  class="layui-edge-right"/>
                            </c:if>
                            <c:if test="${data.id==null}">
                                <input type="text" id="proAdvertImg" name="proAdvertImg" readonly="readonly"  value=""  class="layui-edge-right"/>
                            </c:if>
                            <input type="file" id="file_photo" name="file_photo"  onchange="WW.AjaxUploadImage('file_photo','proAdvertImg',150)" />
                            <img src="${data.proAdvertImg}" id="img_proAdvertImg" alt="产品广告图" height="150" width="150"/>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">分类banner图：</label>
                        <div class="layui-input-block">
                            <c:if test="${data.id!=null}">
                                <input type="text" id="classify" name="classify" readonly="readonly"  value="${data.classify.substring(data.classify.lastIndexOf("/")+1)}"  class="layui-edge-right"/>
                            </c:if>
                            <c:if test="${data.id==null}">
                                <input type="text" id="classify" name="classify" readonly="readonly"  value=""  class="layui-edge-right"/>
                            </c:if>
                            <input type="file" id="file_img" name="file_img"  onchange="WW.AjaxUploadImage('file_img','classify',150)" />
                            <img src="${data.classify}" id="img_classify" alt="产品广告图" height="150" width="150"/>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">产品缩略图：</label>
                        <div class="layui-input-block">
                            <c:if test="${data.id!=null}">
                                <input type="text" id="proThumbnail" name="proThumbnail" readonly="readonly"  value="${data.proThumbnail.substring(data.proThumbnail.lastIndexOf("/")+1)}"  class="layui-edge-right"/>
                            </c:if>
                            <c:if test="${data.id==null}">
                                <input type="text" id="proThumbnail" name="proThumbnail" readonly="readonly"  value=""  class="layui-edge-right"/>
                            </c:if>
                            <input type="file" id="file_image1" name="file_image1"  onchange="WW.AjaxUploadImage('file_image1','proThumbnail',150)" />
                            <img src="${data.proThumbnail}" id="img_proThumbnail" alt="产品广告图" height="150" width="150"/>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">产品展示：</label>
                        <div class="layui-input-block">
                            <textarea id="proDisplay" name="proDisplay" style="width:100%;height:260px;" data-options="" ><span id="aaa"></span>${data.proDisplay}</textarea>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">生产许可证：</label>
                        <div class="layui-input-block">
                            <input type="text" id="productLicense" name="productLicense"placeholder="SC10622030200432" class="layui-input" value="${data.productLicense}"/>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">产品规格：</label>
                        <div class="layui-input-block">
                            <input type="text" id="productSize" name="productSize"placeholder="445ml*10" class="layui-input" value="${data.productSize}"/>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">保质期：</label>
                        <div class="layui-input-block">
                            <input type="text" id="qualityPeriod" name="qualityPeriod"placeholder="12个月" class="layui-input" value="${data.qualityPeriod}"/>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">附加信息：</label>
                        <input type="hidden" name="extraInfo" id="extraInfo" value="${data.extraInfo}">
                        <div class="layui-input-block extra_1">
                            <input type="text" id="extraName_1" name="extraName" style="display: inline;width:42.8%;" placeholder="安全标准" class="layui-input" value=""/>：
                            <input type="text" style="display: inline;width:42.8%;" id="extraInfo_1" name="extInfo"placeholder="IB16546651" class="layui-input" value=""/>
                            <i style="display:inline;font-size: 30px;"  id="extraIcon_1" class="layui-icon layui-icon-add-1" onclick="add()"></i>
                        </div>
                    </div>


                    <h5 class="re-little-title">奖励设置：</h5>

                    <div class="layui-form-item">
                        <label class="layui-form-label">扫码获得</label>
                        <div class="layui-input-inline">
                            <span id="rewardSpan"></span>
                            <input type="number"  name="reward" id="reward" value="${data.reward}" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');" lay-verify="" placeholder="" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid">个token奖励</div>
                    </div>



                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <div class="re-label">
                                <div class="layui-form-mid">勋章奖励种类：</div>
                                <div class="layui-input-inline">
                                    <c:set   var="medalid" value="${data.medalId}" scope="request" />
                                    <select name="medalId" id="medalId">
                                        <option value="0">==请选择==</option>
                                        <c:forEach items="${medalList}" var="medalList" varStatus="vs">
                                            <option value="${medalList.id}" <c:if test="${medalList.id==medalid}">selected</c:if> > ${medalList.medalName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="layui-inline">
                            <div class="layui-form-mid">勋章获得条件：</div>
                            <label class="layui-form-label">有效验证</label>
                            <div class="layui-input-inline">
                                <input type="text" name="rewardWhere" value="${data.rewardWhere}" id="rewardWhere" lay-verify="" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux">个</div>
                        </div>
                    </div>

                    <div class="layui-form-item re-center">
                        <button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="saveProduct">保存</button>
                        <button class="layui-btn layui-btn-primary" lay-submit="" lay-filter="">返回</button>
                    </div>
                </form>

            </div>

        </div>
    </div>

</div>
<jsp:include page="main.jsp"></jsp:include>
<script src="<%=path%>/public/js/wwlib.js" type="text/javascript"></script>
<script src="<%=path%>/public/js/frame.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/public/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=path%>/public/js/ajaxfileupload.js"></script>
<script src="<%=path%>/public/layui-master/src/layui.js" charset="utf-8"></script>
<script src="<%=path%>/public/js/layui-use-js.js"></script>
<script src="<%=path%>/public/js/layui-use-common.js"></script>
<%--<script src="<%=path%>/public/js/product.js" type="text/javascript"></script>--%>
<script type="text/javascript">
    WW.initUEditor('proDisplay');
</script>
</body>
</html>
