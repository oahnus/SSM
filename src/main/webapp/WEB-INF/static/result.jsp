<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-1.8.2.min.js" ></script>
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-form.js" ></script>

    <script language="JavaScript">
        function test(){
            var options = {
                success:function (data) {
                    console.log(data);
                }
            }

            console.info($("#file").val());

            $("#tf").ajaxSubmit(options);
        }
        function download() {
            var taskId = $("#taskId").val();
            var studentId = $("#studentId").val();

//            构建空表单下载课程附件
            var form=$("<form>");//定义一个form表单
            form.attr("style","display:none");
            form.attr("method","get");
            form.attr("action","/ShiXun-1.0-SNAPSHOT/api/student/result/download/"+taskId+"/"+studentId);
            $("body").append(form);//将表单放置在web中
            console.log(form.attr("action"));
            form.submit();
        }
    </script>
</head>
<body>
    <form id="tf" action="/ShiXun-1.0-SNAPSHOT/api/student/result/upload" method="post">
        <br>
        任务id<input type="text" name="taskId"/><br>
        学生学好<input type="text" name="studentId"/><br>
        成果<input id="file" type="file" name="resultFile"/><br>
        <input type="button" value="上传" onclick="test();"/><br>
    </form>

    <br>
    <input type="text" name="id" id="taskId"><br>
    <input type="text" name="id" id="studentId"><br>
    <input type="button" value="下载" onclick="download()">
</body>
</html>
