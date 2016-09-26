<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>submitUserList_4</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <script language="JavaScript" src="/ShiXun-1.0-SNAPSHOT/resource/jquery-1.8.2.min.js" ></script>
    <script type="text/javascript" language="JavaScript">
        function submitUserList_3() {
            var jdata = {"data":[{"username":$("#username").val(),"password":$("#password").val(),"actor":$("#actor").val()}]};

            console.log(jdata);
            console.log(jdata.data);

            $.ajax({
                url: "/ShiXun-1.0-SNAPSHOT/api/user/verify",
                type: "POST",
                contentType : 'application/json;charset=utf-8', //设置请求头信息
                dataType:"json",
                //data: JSON.stringify(customerArray),    //将Json对象序列化成Json字符串，JSON.stringify()原生态方法
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
<h1>Test</h1>
<form id="form1">
    <input id="username" type="text" name="username">
    <input id="password" type="password" name="password">
    <select id="actor" name="actor">
        <option value="1">管理员</option>
        <option value="2">企业</option>
        <option value="3">教师</option>
        <option value="4">学生</option>
    </select>

    <input type="button" value="submit" onclick="submitUserList_3();">
</form>
</body>
</html>