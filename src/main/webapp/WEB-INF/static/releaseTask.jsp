<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-1.8.2.min.js" ></script>
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-form.js" ></script>
    <script language="JavaScript">
        function test(){

            console.log($("#start").val());
            console.info($("#end").val());
            console.info($("#end").val());
            console.info(new Date($("#e").val()).getTime());

            $("#end").val(new Date($("#e").val()).getTime());
            $("#start").val(new Date($("#s").val()).getTime());

            var options = {
                success:function (data) {
                    console.log(data);
                }
            }

            console.log($("#content").val());
            console.log($("#start").val());
            console.log($("#end").val());


            $("#tf").ajaxSubmit(options);
        }
    </script>
</head>
<body>
开始时间<input type="date" id="s" name="startTime"/><br>
结束时间<input type="date" id="e" name="endTime"/><br>
<form id="tf" action="/ShiXun-1.0-SNAPSHOT/api/task/release" method="post">
    任务名<input type="text" name="name"/><br>
    课程id<input type="text" name="courseId"/><br>
    开始时间<input type="text" id="start" name="startTime"/>
    结束时间<input type="text" id="end" name="endTime"/>
    内容<input type="file" name="content" id="content">
    <input type="button" value="保存" onclick="test();"/><br>
</form>
</body>
</html>
