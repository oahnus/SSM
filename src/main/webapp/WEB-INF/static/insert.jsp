<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-1.8.2.min.js" ></script>
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-form.js" ></script>
    <script language="JavaScript">
        function test(type){
            var form = new FormData(document.getElementById("tf"));

            var url;
            if(type === 1){
                url = "/ShiXun-5/api/teacher/insert";
            }else if(type === 2){
                url = "/ShiXun-5/api/student/insert"
            }else if(type === 3){
                url = "/ShiXun-5/api/company/insert"
            }
            var options = {
                success:function (data) {
                    console.log(data.data);
                    console.log(data.status);
                }
            }

            $("#tf").ajaxSubmit(options);
        }
    </script>
</head>
<body>
<form id="tf" action="/ShiXun-1.0-SNAPSHOT/api/teacher/insert" method="post">
    <br>
    导入Excel<input type="file" name="excelFile"/><br>
    导入教师<input type="button" value="保存" onclick="test(1);"/><br>
    <%--导入学生<input type="button" value="保存" onclick="test(2);"/><br>--%>
    <%--导入公司<input type="button" value="保存" onclick="test(3);"/><br>--%>
</form>
</body>
</html>
