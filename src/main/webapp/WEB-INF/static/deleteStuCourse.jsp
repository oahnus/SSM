<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量删除教师</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-1.8.2.min.js" ></script>
    <script type="text/javascript" language="JavaScript">
        function submitUserList_3() {

            var ids = {data:[{id:$("#id").val()}]};

            $.ajax({
                url: "/ShiXun-1.0-SNAPSHOT/api/stucourse/delete",
                type: "POST",
                contentType : 'application/json;charset=utf-8', //设置请求头信息
                dataType:"json",
                //data: JSON.stringify(customerArray),    //将Json对象序列化成Json字符串，JSON.stringify()原生态方法
                data: JSON.stringify(ids),           //将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js
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
<h1>Test</h1>
<form id="form1">
    批量删除id
    <input type="text" id="id">
    <input type="button" value="submit" onclick="submitUserList_3();">
</form>
</body>
</html>