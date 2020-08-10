<%--
  Created by IntelliJ IDEA.
  User: geek
  Date: 2020/8/7
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>窗口1</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<%--    <script src="/res/js/sockjs.min.js"></script>--%>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
</head>
<body>
<div id="msg">213</div>
<input id="tx" />
<button id="send_btn">发送</button>
</body>
<script>
    $(function () {
        var websocket;

        // 首先判断是否 支持 WebSocket
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://localhost:8080/websocket.html");
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://localhost:8080/websocket.html");
        } else {
            websocket = new SockJS("http://localhost:8080/sockjs/websocket.html");
        }

        // 打开时
        websocket.onopen = function (evnt) {
            console.log("  websocket.onopen  ");
        };


        // 处理消息时
        websocket.onmessage = function (evnt) {
            var json = JSON.parse(evnt).data[0].payload;
            $("#msg").append("<p>(<font color='red'>" + evnt.data + "</font>)</p>");
            console.log("  websocket.onmessage   ");
        };


        websocket.onerror = function (evnt) {
            console.log("  websocket.onerror  ");
        };

        websocket.onclose = function (evnt) {
            console.log("  websocket.onclose  ");
        };

        $("#send_btn").click(function () {
            var text = $("#tx").val();
            var msg = {
                msgContent: text,
                postsId: 1
            };
            websocket.send(JSON.stringify(msg));
        });
    });


</script>


</html>
