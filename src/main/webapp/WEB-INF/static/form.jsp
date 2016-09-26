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

            $("#tf").ajaxSubmit(options);
        }
        function download() {
            var id = $("#id").val();

//            构建空表单下载课程附件
            var form=$("<form>");//定义一个form表单
            form.attr("style","display:none");
            form.attr("method","get");
            form.attr("action","/ShiXun-1.0-SNAPSHOT/api/course/download/"+id);
            $("body").append(form);//将表单放置在web中
            console.info("/ShiXun-1.0-SNAPSHOT/api/course/download/"+id);
            console.log(form.attr("action"));
            form.submit();
        }
    </script>
</head>
<body>
    <form id="tf" action="/ShiXun-1.0-SNAPSHOT/api/course/release" method="post">
        <br>
        课程名<input type="text" name="name"/><br>
        专业<input type="text" name="profession"/><br>
        教师<input type="text" name="teacher"/><br>
        公司<input type="text" name="company"/><br>
        描述<input type="text" name="memo"/><br>
        附件<input type="file" name="addition"/><br>
        <input type="button" value="保存" onclick="test();"/><br>
    </form>

    <br>
    <input type="text" name="id" id="id"><br>
    <input type="button" value="下载" onclick="download()">
</body>
</html>
