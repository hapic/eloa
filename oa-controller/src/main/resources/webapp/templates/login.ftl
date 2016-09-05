<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登陆</title>
    <link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">


</script>
<body>

<form class="form-horizontal">
    <div class="form-group">
        <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
        <div class="col-sm-10">
            <input type="text" id="userName" placeholder="userName" name="userName">
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
        <div class="col-sm-10">
            <input type="password" id="password" placeholder="password" name="password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="checkbox">
                <label>
                    <input type="checkbox"> Remember me
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">Sign in</button>
        </div>
    </div>
</form>

<hr/>
<form class="form-horizontal" action="/kq/login" method="post">
    <div class="control-group">
        <label class="control-label" for="inputEmail">Email</label>
        <div class="controls">
            <input type="text" id="userName" placeholder="userName" name="userName">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="inputPassword">Password</label>
        <div class="controls">
            <input type="password" id="password" placeholder="password" name="password">
        </div>
    </div>
    <div class="control-group">
        <div class="controls">

            <button type="submit" class="btn">登陆</button>
        </div>
    </div>
</form>


</body>
</html>