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

3) 避免在WHERE子句中使用in，not  in，or 或者having。l
可以使用 exist 和not exist代替 in和not in。
可以使用表链接代替 exist。
Having可以用where代替，如果无法代替可以分两步处理。

举个栗子：
SELECT * FROM ORDERS WHERE CUSTOMER_NAME NOT IN  
(SELECT CUSTOMER_NAME FROM CUSTOMER)  

优化：
SELECT * FROM ORDERS WHERE CUSTOMER_NAME not exist  
(SELECT CUSTOMER_NAME FROM CUSTOMER)
4) 不要在建立的索引的数据列上进行下列操作:
（1）避免对索引字段进行计算操作
（2）避免在索引字段上使用not，<>，!=
（3）避免在索引列上使用IS NULL和IS NOT NULL
（4）避免在索引列上出现数据类型转换
（5）避免在索引字段上使用函数
例如：where trunc(create_date)=trunc(:date1)
虽然已对create_date 字段建了索引，但由于加了TRUNC，使得索引无法用上。此处正确的写法应该是
where create_date>=trunc(:date1) and create_date
（6）避免建立索引的列中使用空值。



2) 数据库表结构的优化
3) 系统配置的优化
4) 硬件优化


还有一种很重要但易被大家忽略的方法：大表数据分割。当一个表的数据规模达到数亿条时，索引已基本发挥不了作用:建立索引要花费大量时间，查询时由于要扫描大的索引表也要花费大量时间。为了发挥索引的作用，可以将大表按照某个字段拆分为若干个小表。
例如，国内某大型保险公司，其有36家分公司，一年的保单明细表(f_policy)大概有2亿条记录,两年的数据超过4亿条，如果在f_policy上作一次查询，响应非常慢，可以考虑将f_policy按照机构拆分为36个同构的小表，在作整个保单明细表的查询时，可以使用union all操作合并数据，或者建立一个union all的视图，查询效率大大提高。并且，作这样的拆分非常有用，因为经常会有只查询某个分公司数据的需求。




浏览器中输入URL到返回页面的全过程:
第一步，解析域名，找到主机IP
第二步，浏览器与网站建立TCP连接
第三步，浏览器发起GET请求
第四步，显示页面或返回其他





几个重要的java数据库访问类和接口
DriverManager类处理驱动程序的加载和建立新数据库连接
Connection类:
Statement类:Statement类是java.sql包中用于在指定的连接中处理SQL语句的类。
ResultSet类:


MySql大批量插入数据的方法:
1.使用事务来插入：使用拼接sql的方法，在批量插入的时候，mysql支持这种语法(不推荐)
2.数据通过生成文件导入数据库:mysql支持把csv文件倒入到数据库，生成csv文件的时候，不需要特别生成一行头文件，每行生成的数据直接用 ',' 分隔就行，一行代表数据库的一条记录，所以生成csv文件时候，记得数据的顺序


MyBatis批量操作：
mysql需要数据库连接配置&allowMultiQueries=true
jdbc.url=jdbc:mysql://mbp-mysql-xm01:5002/xmppsc?autoReconnect=true&useUnicode=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true




mybatis 中 foreach collection的三种用法
1. 如果传入的是单参数且参数类型是一个List的时候，collection属性值为list
2. 如果传入的是单参数且参数类型是一个array数组的时候，collection的属性值为array
3. 如果传入的参数是多个的时候，我们就需要把它们封装成一个Map了，当然单参数也可


spring mvc 在同一个controller 中同时返回多种格式的数据 (xml json atom)
具体使用到org.springframework.web.servlet.view.ContentNegotiatingViewResolver类
 <bean class="org.springframework.web.servlet.view.ContentNegotiatingViewResolver">
    <property name="mediaTypes">
        <map>
            <entry key="atom" value="application/atom+xml"/>
            <entry key="html" value="text/html"/>
            <entry key="json" value="application/json"/>
        </map>
    </property>
	
	
	
	tcp和udp的区别
TCP---传输控制协议,提供的是面向连接、可靠的字节流服务。当客户和服务器彼此交换数据前，必须先在双方之间建立一个TCP连接，之后才能传输数据。TCP提供超时重发，丢弃重复数据，检验数据，流量控制等功能，保证数据能从一端传到另一端。
UDP---用户数据报协议，是一个简单的面向数据报的运输层协议。UDP不提供可靠性，它只是把应用程序传给IP层的数据报发送出去，但是并不能保证它们能到达目的地。由于UDP在传输数据报前不用在客户和服务器之间建立一个连接，且没有超时重发等机制，故而传输速度很快
TCP总结：
Server端：create -- bind -- listen--  accept--  recv/send-- close
Client端：create------- conncet------send/recv------close.

UDP总结:
Server端：create----bind ----recvfrom/sendto----close
Client端：create----  sendto/recvfrom----close.


  OSI（Open System Interconnect），即开放式系统互联
OSI定义了网络互连的七层框架（物理层、数据链路层、网络层、传输层、会话层、表示层、应用层），即ISO开放互连系统参考模型  
1.应用层（为应用程序提供服务）：OSI参考模型中最靠近用户的一层，是为计算机用户提供应用接口，也为用户直接提供各种网络服务。我们常见应用层的网络服务协议有：HTTP，HTTPS，FTP，POP3、SMTP等。
实际公司A的老板就是我们所述的用户，而他要发送的商业报价单，就是应用层提供的一种网络服务，当然，老板也可以选择其他服务，比如说，发一份商业合同，发一份询价单，等等。
2.表示层（数据格式转化，数据加密）提供各种用于应用层数据的编码和转换功能,确保一个系统的应用层发送的数据能被另一个系统的应用层识别。如果必要，该层可提供一种标准表示形式，用于将计算机内部的多种数据格式转换成通信中采用的标准表示形式。数据压缩和加密也是表示层可提供的转换功能之一。
  由于公司A和公司B是不同国家的公司，他们之间的商定统一用英语作为交流的语言，所以此时表示层（公司的文秘），就是将应用层的传递信息转翻译成英语。同时为了防止别的公司看到，公司A的人也会对这份报价单做一些加密的处理。这就是表示的作用，将应用层的数据转换翻译等。
3.会话层（建立，管理和维护会话）：会话层就是负责建立、管理和终止表示层实体之间的通信会话。该层的通信由不同设备中的应用程序之间的服务请求和响应组成。    
会话层的同事拿到表示层的同事转换后资料，（会话层的同事类似公司的外联部），会话层的同事那里可能会掌握本公司与其他好多公司的联系方式，这里公司就是实际传递过程中的实体。他们要管理本公司与外界好多公司的联系会话。当接收到表示层的数据后，会话层将会建立并记录本次会话，他首先要找到公司B的地址信息，然后将整份资料放进信封，并写上地址和联系方式。准备将资料寄出。等到确定公司B接收到此份报价单后，此次会话就算结束了，外联部的同事就会终止此次会话。
4.传输层（建立、管理和维护端到端的连接）：传输层建立了主机端到端的链接，传输层的作用是为上层协议提供端到端的可靠和透明的数据传输服务，包括处理差错控制和流量控制等问题。该层向高层屏蔽了下层数据通信的细节，使高层用户看到的只是在两个传输实体间的一条主机到主机的、可由用户控制和设定的、可靠的数据通路。我们通常说的，TCP UDP就是在这一层。端口号既是这里的“端”。
传输层就相当于公司中的负责快递邮件收发的人，公司自己的投递员，他们负责将上一层的要寄出的资料投递到快递公司或邮局。
5.网络层（IP选址及路由选择）
本层通过IP寻址来建立两个节点之间的连接，为源端的运输层送来的分组，选择合适的路由和交换节点，正确无误地按照地址传送给目的端的运输层。就是通常说的IP层。这一层就是我们经常说的IP协议层。IP协议是Internet的基础。
网络层就相当于快递公司庞大的快递网络，全国不同的集散中心，比如说，从深圳发往北京的顺丰快递（陆运为例啊，空运好像直接就飞到北京了），首先要到顺丰的深圳集散中心，从深圳集散中心再送到武汉集散中心，从武汉集散中心再寄到北京顺义集散中心。这个每个集散中心，就相当于网络中的一个IP节点。
6.数据链路层（提供介质访问和链路管理）:将比特组合成字节,再将字节组合成帧,使用链路层地址 (以太网使用MAC地址)来访问介质,并进行差错检测。
     数据链路层又分为2个子层：逻辑链路控制子层（LLC）和媒体访问控制子层（MAC）。
        MAC子层处理CSMA/CD算法、数据出错校验、成帧等；LLC子层定义了一些字段使上次协议能共享数据链路层。 在实际使用中，LLC子层并非必需的。

7.物理层:  实际最终信号的传输是通过物理层实现的。通过物理介质传输比特流。规定了电平、速度和电缆针脚。常用设备有（各种物理设备）集线器、中继器、调制解调器、网线、双绞线、同轴电缆。这些都是物理层的传输介质。
 快递寄送过程中的交通工具，就相当于我们的物理层，例如汽车，火车，飞机，船。
 
 
 
 javas死锁：
 
"Thread-1" #11 prio=5 os_prio=0 tid=0x0000000018c23000 nid=0x1ca4 waiting for monitor entry [0x000000001994f000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.lock.DeadLock.rightLeft(DeadLock.java:27)
        - waiting to lock <0x00000000d575cbf8> (a java.lang.Object)
        - locked <0x00000000d575cc08> (a java.lang.Object)
        at com.lock.Thread1.run(Thread1.java:16)

"Thread-0" #10 prio=5 os_prio=0 tid=0x0000000018c22800 nid=0x3084 waiting for monitor entry [0x0000000019bbf000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.lock.DeadLock.leftRight(DeadLock.java:15)
        - waiting to lock <0x00000000d575cc08> (a java.lang.Object)
        - locked <0x00000000d575cbf8> (a java.lang.Object)
        at com.lock.Thread0.run(Thread0.java:16)
	先说明介绍一下每一部分的意思，以"Thread-1"为例：

（1）"Thread-1"表示线程名称

（2）"prio=6"表示线程优先级

（3）"tid=00000000497cec00"表示线程Id

（4）nid=0x219c

线程对应的本地线程Id，这个重点说明下。因为Java线程是依附于Java虚拟机中的本地线程来运行的，实际上是本地线程在执行Java线程代码，只有本地线程才是真正的线程实体。Java代码中创建一个thread，虚拟机在运行期就会创建一个对应的本地线程，而这个本地线程才是真正的线程实体。Linux环境下可以使用"top -H -p JVM进程Id"来查看JVM进程下的本地线程（也被称作LWP）信息，注意这个本地线程是用十进制表示的，nid是用16进制表示的，转换一下就好了，0x219c对应的本地线程Id应该是8604。

（5）"[0x000000004a3bf000..0x000000004a3bf790]"表示线程占用的内存地址

（6）"java.lang.Thread.State：BLOCKED"表示线程的状态

解释完了每一部分的意思，看下Thread-1处于BLOCKED状态，Thread-0处于BLOCKED状态。对这两个线程分析一下：

（1）Thread-1获得了锁0x000000003416a4e8，在等待锁0x000000003416a4d8

（2）Thread-0获得了锁0x000000003416a4d8，在等待锁0x000000003416a4e8

产生死锁的四个必要条件：
（1） 互斥条件：一个资源每次只能被一个进程使用。
（2） 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
（3） 不剥夺条件:进程已获得的资源，在末使用完之前，不能强行剥夺。
（4） 循环等待条件:若干进程之间形成一种头尾相接的循环等待资源关系。
	
		