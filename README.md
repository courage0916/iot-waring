# Project Introduction（项目介绍）
This is an alarm system where IoT devices issue warnings and then notify the responsible person via SMS or phone

这是一个警报系统，物联网设备发出警告，然后通过短信或电话通知负责人

# Open source reasons（开源原因）
I took on a private job worth 5000 yuan, trust acquaintances, and did not sign a contract. As a result, he made four changes to hardware requirements, three changes to page usage requirements, and one change to software mechanism requirements. 

In the end, I was nitpicking, saying that the Alibaba Cloud database I purchased, which is deployed on the internal network without developing external ports, may be attacked by hackers. I was asked to use code to implement a full system data backup.

I can't stand that stupid guy's demands anymore. The project was deployed and launched in 10 days. However, the acceptance was terminated based on the non functional requirements mentioned above, and I was asked to make free changes until the 25th day. The final payment of 3000 yuan was not paid, and the last point was made about the database issue. The software system I created was criticized for being junk.

I meticulously completed his initial requirements, cooperated with him three or four times to complete the changes without any additional requirements given to me in the requirement research. He also greedily hoped that I would do more extra things for him. He is a dishonest person, and I don't want to work with him anymore.

In the end, I deleted the online deployed service, refunded him a deposit of 1000 yuan, and charged him a breach of contract fee of 1000 yuan as my help in selecting servers, databases, applying for SMS templates, voice templates, and applying for and filing domain names, which already existed but could not be deleted. In a state of extraordinary loss, I terminated this contract and ended our friendship with this acquaintance.

I have complete rights to the code that has already been written, and he may think that the remaining code will be my loss.

However, due to the presence of the community, I believe that these codes will become my valuable asset in the future.

Colleague, also because I have gained some knowledge in the community and have the idea of giving back to the community.

So, the code for this project is now open source, and if you need it, you can take it yourself. The front-end and back-end are all within this project.

Additionally, if you have any projects that require development, you can also contact me. I have the ability to develop and deploy projects on one line.

If you want to invite the author for a cup of coffee, you can also use the following channels:



接了个5000块的私活，相信熟人，没有签合同。结果，他更改了四次硬件需求，三次页面使用需求，一次软件机制需求。最后还鸡蛋挑骨头，说购买的阿里云那部署在内网没有开发外网端口的数据库，有可能被黑客攻击，要求我再用代码实现一次全系统数据备份。

我实在忍受不了那家伙的要求了。项目10天就部署上线了，可一直以上面的非事先说明的非功能性需求，终止验收，并让我免费更改，直到拖到了第二十五天，还不给我结3000的尾款，还用最后一点说提出的数据库的问题，鸡蛋挑骨头的谩骂我做的软件系统是垃圾。

我一丝不苟的完成了他的初始需求，三番四次的配合他完成了更改的没有在需求调研上给我的额外需求，他还贪婪的希望我为他做更多的额外的事，他是一个不讲信用的人，我不想和他合作了。

最终，以我删掉线上部署的服务，退他1000块定金，收1000违约费作为我帮助他选购服务器、数据库、申请短信模版、语音模版、申请并备案域名这些已经存在却没办法删除劳动的损失，以一种非常亏本的状态，解除了这个合同，并结束了和这个熟人的交情。

已经写完的代码，我有完整的权利，他或许以为这写留着的代码会是我的损失。

但是，因为社区的存在，我相信这些代码在未来会成为我宝贵的财富。

同时，也因为我也在社区获取了一些知识，有回报社区的想法。

所以，这个项目的代码现在开源，大家有需要的话自取，前端、后端、都在这个项目内。

另外，大家如果有项目需要开发，也可以联系我，我拥有一条线开发并部署项目的能力。

如果想请作者喝杯咖啡，也可以通过下面渠道：

支付宝：![微信图片_20240327095324](https://github.com/courage0916/iot-waring/assets/43770682/aac38949-2396-4b81-8a2e-07cfee54ace6)

微信：![微信图片_20240327095330](https://github.com/courage0916/iot-waring/assets/43770682/eb728b92-4251-4a73-a9fa-4d4b6dc22684)


# Function Introduction（功能介绍）
## 1：Login（登录）
### 1.1：login check （登录校验）
#### The username and password cannot be empty（用户名密码不能为空）    
### 1.2：user check （用户校验）
#### Only one user can use the system at the same time（同一时间使用该系统的只能有一个用户）    
## 2：Device Management（设备管理）
### 2.1：device crud （设备的增删改查）
#### Basic equipment addition, deletion, modification, and inspection（基础的设备增删改查）
### 2.2：Equipment information collection （设备信息采集）
#### Upload JSON format data to software server through TCP protocol（通过tcp协议上传json格式的数据到软件服务器）
### 2.3：Information deduplication （信息去重）
#### There may be multiple IoT devices sending the same device data, only consuming one for alarm purposes, while others will receive normal returns（可能有多个iot设备发送相同的设备数据，只消费一条用以告警，其他的给予正常返回）
## 3：User Management（用户管理）
### 3.1：user crud （用户的增删改查）
#### Basic user additions, deletions, modifications, and queries（基础的用户增删改查）
#### When deleting a user, delete the devices under that user's banner at the same time（删除一个用户时，用同时删除该用户旗下的设备）
### 3.2：user view （用户视角）
#### Administrators can view all devices, while operators can only view their own devices（管理员可以看所有设备，操作员只能看自己的设备）
## 4：Administrator reminder configuration（管理员提醒配置）
### 4.1：timed reminder（定时提醒）
#### Administrators can set a time schedule to obtain the status of the server（管理员可以设置一个时间定时获取服务器的状态）
## 5：other（其他）
### 5.1：Verification of some business logic（一些业务逻辑的校验）
# Renderings（效果图）
## English（英语）

## Chinese（中文）
# Collaboration notes（合作备注）
## If you have some development project requirements and happen to think I am suitable, you can contact me for communication. The project requires a contract to be signed, and I hope you have the spirit of the contract, not frequently mentioning requirements beyond those agreed upon outside the contract, or paying additional fees for these requirements according to the contract agreement.
## 如果您有一些开发项目的需求，又恰好觉得我合适，可以联系沟通。项目需要签合同，并且我希望您能有契约精神，不经常提超出合同外所约定的需求，或者按照合同协议额外的给这些需求付费。
## There are two forms of cooperation, one is not providing source code, and the other is providing source code. The price of not providing source code will be slightly lower. The way of providing source code involves a supplementary agreement, which is that the buyer of this source code needs 30% modification to resell, and the seller also needs 30% modification to resell. Both buyers and sellers have the right to continue developing and customizing the source code
## 合作分为两种形式，一种是不提供源码的，一种是提供源码的，不提供源码的价格会稍低，提供源码的方式蕴含着一个补充协议，就是这个源码买方需要有30%改动才能转卖，卖方也需要有30%改动才能转卖。买卖双方都对源码拥有继续开发定制的权利

