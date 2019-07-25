# momo-event
### 简单说明
看了几篇akka的用途，趁这几天工作不太忙，实际操练下。基于akka的一个事件处理。可以用到审计上面，比如用户访问了某个接口，可以把相关的事件push到事件中心再来处理。每个事件可以挂载多个handler来进行处理。比如用户在个人中心修改密码，需要emailhandler accounthandler等等。