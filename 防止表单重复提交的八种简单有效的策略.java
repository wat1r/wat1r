防止表单重复提交的八种简单有效的策略
1、js禁掉提交按钮。
表单提交后使用Javascript使提交按钮disable。这种方法防止心急的用户多次点击按钮。但有个问题，如果客户端把Javascript给禁止掉，这种方法就无效了。
2、使用Post/Redirect/Get模式。
在提交后执行页面重定向，这就是所谓的Post-Redirect-Get (PRG)模式。简言之，当用户提交了表单后，你去执行一个客户端的重定向，转到提交成功信息页面。
这能避免用户按F5导致的重复提交，而其也不会出现浏览器表单重复提交的警告，也能消除按浏览器前进和后退按导致的同样问题。
3、在session中存放一个特殊标志。
在服务器端，生成一个唯一的标识符，将它存入session，同时将它写入表单的隐藏字段中，然后将表单页面发给浏览器，用户录入信息后点击提交，在服务器端，获取表单中隐藏字段的值，与session中的唯一标识符比较，相等说明是首次提交，就处理本次请求，然后将session中的唯一标识符移除；不相等说明是重复提交，就不再处理。
4．使用header函数转向
除了上面的方法之外，还有一个更简单的方法，那就是当用户提交表单，服务器端处理后立即转向其他的页面，代码如下所示。
if (isset($_POST['action']) && $_POST['action'] == 'submitted') {
//处理数据，如插入数据后，立即转向到其他页面
header('location:submits_success.php');
}
这样，即使用户使用刷新键，也不会导致表单的重复提交，因为已经转向新的页面，而这个页面脚本已经不理会任何提交的数据了。
5.表单过期的处理
在开发过程中，经常会出现表单出错而返回页面的时候填写的信息全部丢失的情况，为了支持页面回跳，可以通过以下两种方法实现。
1．使用header头设置缓存控制头Cache-control。
header('Cache-control: private, must-revalidate'); //支持页面回跳
2．使用session_cache_limiter方法。
session_cache_limiter('private, must-revalidate'); //要写在session_start方法之前
下面的代码片断可以防止用户填写表单的时候，单击“提交”按钮返回时，刚刚在表单上填写的内容不会被清除：
session_cache_limiter('nocache');
session_cache_limiter('private');
session_cache_limiter('public');
session_start();
//以下是表单内容，这样在用户返回该表单时，已经填写的内容不会被清空
将该段代码贴到所要应用的脚本顶部即可。
6.判断表单动作的技巧
表单可以通过同一个程序来分配应该要处理的动作，在表单中有不同的逻辑，要怎么判别使用者按下的按钮内容不过是个小问题。
其实只要通过提交按钮的name 就可以知道了，表单在提交出去的时候，只有按下的submit类型的按钮才会被送到表单数组去，所以只要判断按钮的值就可以知道使用者按下哪一个按钮，以如下表单为例：
<FORM method="POST" Action=test.php>
<input type=submit name="btn" value="a">
<input type=submit name="btn" value="b">
</FORM>
当使用者按下“a”按钮的时候btn=a，按下“b”按钮，则btn=b。
另外也可以通过提交按钮的名字（name）来判断，请见如下代码：
<FORM method="POST" Action=test.php>
<input type=submit name="a" value="提交A">
<input type=submit name="b" value="提交B">
</FORM>
这样只要POST/GET的参数里面有a或b，就可以知道按下的按钮是哪个。
<?php
print_r($_POST);
?>

7、在数据库里添加约束。
在数据库里添加唯一约束或创建唯一索引，防止出现重复数据。这是最有效的防止重复提交数据的方法。
8.使用Cookie处理
使用Cookie记录表单提交的状态，根据其状态可以检查是否已经提交表单，请见下面的代码：
<?php
if(isset($_POST['go'])){
setcookie("tempcookie","",time()+30);
header("Location:".$_SERVER[PHP_SELF]);
exit();
}
if(isset($_COOKIE["tempcookie"])){
setcookie("tempcookie","",0);
echo "您已经提交过表单";
}
?>



数据库调优的经验：
1) SQL语句及索引的优化
带有DISTINCT,UNION,MINUS,INTERSECT,ORDER BY的SQL语句会启动SQL引 执行，耗费资源的排序(SORT)功能。DISTINCT需要一次排序操作, 而其他的至少需要执行两次排序
2) 如果无需排除重复值或是操作集无重复则用UNION ALL， UNION更费事（因为要比较）
UNION因为会将各查询子集的记录做比较，故比起UNION ALL ，通常速度都会慢上许多。一般来说，如果使用UNION ALL能满足要求的话， 务必使用UNION ALL。还有一种情况大家可能会忽略掉，就是虽然要求几个子集的并集需要过滤掉重复记录，但由于脚本的特殊性，不可能存在重复记录，这时便应该使用UNION ALL，如xx模块的某个查询程序就曾经存在这种情况，见，由于语句的特殊性，在这个脚本中几个子集的记录绝对不可能重复，故可以改用UNION ALL）连接操作

可以使用 exist 和not exist代替 in和not in。
可以使用表链接代替 exist。
Having可以用where代替，如果无法代替可以分两步处理。

举个栗子：
SELECT * FROM ORDERS WHERE CUSTOMER_NAME NOT IN  
(SELECT CUSTOMER_NAME FROM CUSTOMER)  

优化：
SELECT * FROM ORDERS WHERE CUSTOMER_NAME not exist  
(SELECT CUSTOMER_NAME FROM CUSTOMER)




2) 数据库表结构的优化
3) 系统配置的优化
4) 硬件优化


