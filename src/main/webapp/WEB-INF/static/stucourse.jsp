<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-1.8.2.min.js" ></script>
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-form.js" ></script>
    <script language="JavaScript">
        function test(){
            var jdata = {data:[{"courseId":$("#courseId").val(),"studentId":$("#studentId").val()}]};

            console.info(jdata);

            $.ajax({
                url: "/ShiXun-1.0-SNAPSHOT/api/stucourse/insert",
                type: "POST",
                contentType : 'application/json;charset=utf-8', //设置请求头信息
                dataType:"json",
                data:JSON.stringify(jdata),           //将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js
                success: function(data){
                    console.log(data.status);
                    console.log(data);
                    console.log(data.data);
                },
                error: function(res){
                    console.log(res.responseText);
                }
            });
        }
    </script>
</head>
<body>
<form id="tf" action="/ShiXun-1.0-SNAPSHOT/api/stucourse/insert" method="post">
    <br>
    <input type="text" name="courseId" id="courseId"/><br>
    <input type="text" name="studentId" id="studentId"/><br>
    <input type="button" value="保存" onclick="test();"/><br>
</form>
</body>
</html>
