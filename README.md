# 基于Spring-boot的个人博客实现

## 前置知识（选看）

### 1. Spring 简介

- [Spring官网](http://spring.io/)
- [官方下载地址](https://repo.spring.io/libs-release-local/org/springframework/spring/)
- [Github资源](https://github.com/spring-projects)

> 优点

1. Spring是一个开源免费的框架 , 容器 .
2. Spring是一个轻量级的框架 , 非侵入式的 .
3. 控制反转 IoC , 面向切面 Aop.
4. 对事物的支持 , 对框架的支持.

> 总结：Spring是一个轻量级的控制反转(IOC)和面向切面(AOP)的容器（框架）。

### 2. 组成

![spring组成]($%7Bstatic%7D/t_20200616000249462864.jpg)

**Spring 框架是一个分层架构，由 7 个定义良好的模块组成。Spring 模块构建在核心容器之上，核心容器定义了创建、配置和管理 bean 的方式。**

![核心]($%7Bstatic%7D/u=519275253,3573859629&fm=26&gp=0_20200616000436012593.jpg)

**Spring 框架的每个模块（或组件）都可以单独存在，或者与其他一个或多个模块联合实现。每个模块的功能如下：**

> - 核心容器：核心容器提供 Spring 框架的基本功能。核心容器的主要组件是 BeanFactory，它是工厂模式的实现。BeanFactory 使用控制反转（IOC） 模式将应用程序的配置和依赖性规范与实际的应用程序代码分开。
> - Spring 上下文：Spring 上下文是一个配置文件，向 Spring 框架提供上下文信息。Spring 上下文包括企业服务，例如 JNDI、EJB、电子邮件、国际化、校验和调度功能。
> - Spring AOP：通过配置管理特性，Spring AOP 模块直接将面向切面的编程功能 , 集成到了 Spring 框架中。所以，可以很容易地使 Spring 框架管理任何支持 AOP的对象。Spring AOP 模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖组件，就可以将声明性事务管理集成到应用程序中。
> - Spring DAO：JDBC DAO 抽象层提供了有意义的异常层次结构，可用该结构来管理异常处理和不同数据库供应商抛出的错误消息。异常层次结构简化了错误处理，并且极大地降低了需要编写的异常代码数量（例如打开和关闭连接）。Spring DAO 的面向 JDBC 的异常遵从通用的 DAO 异常层次结构。
> - Spring ORM：Spring 框架插入了若干个 ORM 框架，从而提供了 ORM 的对象关系工具，其中包括 JDO、Hibernate 和 iBatis SQL Map。所有这些都遵从 Spring 的通用事务和 DAO 异常层次结构。
> - Spring Web 模块：Web 上下文模块建立在应用程序上下文模块之上，为基于 Web 的应用程序提供了上下文。所以，Spring 框架支持与 Jakarta Struts 的集成。Web 模块还简化了处理多部分请求以及将请求参数绑定到域对象的工作。
> - Spring MVC 框架：MVC 框架是一个全功能的构建 Web 应用程序的 MVC 实现。通过策略接口，MVC 框架变成为高度可配置的，MVC 容纳了大量视图技术，其中包括 JSP、Velocity、Tiles、iText 和 POI。

### 3. IOC本质

> 控制反转IOC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法，也有人认为DI只是IoC的另一种说法。没有IoC的程序中 , 我们使用面向对象编程 , 对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，个人认为所谓控制反转就是：获得依赖对象的方式反转了。

- IoC是Spring框架的核心内容，使用多种方式完美的实现了IoC，可以使用XML配置，也可以使用注解，新版本的Spring也可以零配置实现IoC。
- Spring容器在初始化时先读取配置文件，根据配置文件或元数据创建与组织对象存入容器中，程序使用时再从Ioc容器中取出需要的对象。

![img]($%7Bstatic%7D/u=3277298381,4239731486&fm=26&gp=0_20200616001023081227.jpg)

采用XML方式配置Bean的时候，Bean的定义信息是和实现分离的，而采用注解的方式可以把两者合为一体，Bean的定义信息直接以注解的形式定义在实现类中，从而达到了零配置的目的。

> **控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现控制反转的是IoC容器，其实现方法是依赖注入（Dependency Injection,DI）。**

### 4. 什么是AOP

> AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

**Spring中可以AOP横向切入要执行的事件，却又不改变原来的代码，做到低耦合原则。**

例如要在纵向的DAO层插入日志事件，为了不改变原有的代码就可以使用AOP横向切入代码来记录日志，**SpringMVC中的拦截器的原理也是使用了AOP。**![img]($%7Bstatic%7D/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20200616003550_20200616003612935313.png)

### 5. AOP相关用词

- **横切关注点**：跨越应用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但是我们需要关注的部分，就是横切关注点。如日志 , 安全 , 缓存 , 事务等等 ....

- **切面（ASPECT）**：横切关注点 被模块化 的特殊对象。即，它是一个类。

- **通知（Advice**）：切面必须要完成的工作。即，它是类中的一个方法。

- **目标（Target**）：被通知对象。

- **代理（Proxy**）：向目标对象应用通知之后创建的对象。

- **切入点（PointCut）**：切面通知 执行的 “地点”的定义。

- **连接点（JointPoint**）：与切入点匹配的执行点。

  ![img]($%7Bstatic%7D/u=834113437,490587263&fm=26&gp=0_20200616004105650100.jpg)

### 6. 什么是spring-boot

**Spring是一个开源框架，2003 年兴起的一个轻量级的Java 开发框架，作者：Rod Johnson  。**

**Spring是为了解决企业级应用开发的复杂性而创建的，简化开发。**

#### Spring是如何简化Java开发的

为了降低Java开发的复杂性，Spring采用了以下4种关键策略：

1、基于POJO的轻量级和最小侵入性编程，所有东西都是bean；

2、通过IOC，依赖注入（DI）和面向接口实现松耦合；

3、基于切面（AOP）和惯例进行声明式编程；

4、通过切面和模版减少样式代码，xxxTemplate；



#### 什么是[SpringBoot](https://spring.io/projects/spring-boot)

> 学过javaweb的应该知道，开发一个web应用，从最初开始接触Servlet结合Tomcat, 跑出一个Hello Wolrld程序，是要经历特别多的步骤；后来就用了框架Struts，再后来是SpringMVC，到了现在的SpringBoot，过一两年又会有其他web框架出现；你们有经历过框架不断的演进，然后自己开发项目所有的技术也在不断的变化、改造吗？建议都可以去经历一遍；
>
> 言归正传，什么是SpringBoot呢，就是一个javaweb的开发框架，和SpringMVC类似，对比其他javaweb框架的好处，官方说是简化开发，约定大于配置，  you can "just run"，能迅速的开发web应用，几行代码开发一个http接口。
>
> 所有的技术框架的发展似乎都遵循了一条主线规律：从一个复杂应用场景 衍生 一种规范框架，人们只需要进行各种配置而不需要自己去实现它，这时候强大的配置功能成了优点；发展到一定程度之后，人们根据实际生产应用情况，选取其中实用功能和设计精华，重构出一些轻量级的框架；之后为了提高开发效率，嫌弃原先的各类配置过于麻烦，于是开始提倡“约定大于配置”，进而衍生出一些一站式的解决方案。
>
> 是的这就是Java企业级应用->J2EE->spring->springboot的过程。
>
> 随着 Spring 不断的发展，涉及的领域越来越多，项目整合开发需要配合各种各样的文件，慢慢变得不那么易用简单，违背了最初的理念，甚至人称配置地狱。Spring Boot 正是在这样的一个背景下被抽象出来的开发框架，目的为了让大家更容易的使用 Spring 、更容易的集成各种常用的中间件、开源软件；
>
> Spring Boot 基于 Spring 开发，Spirng Boot 本身并不提供 Spring 框架的核心特性以及扩展功能，只是用于快速、敏捷地开发新一代基于 Spring 框架的应用程序。也就是说，它并不是用来替代 Spring 的解决方案，而是和 Spring 框架紧密结合用于提升 Spring 开发者体验的工具。Spring Boot 以**约定大于配置的核心思想**，默认帮我们进行了很多设置，多数 Spring Boot 应用只需要很少的 Spring 配置。同时它集成了大量常用的第三方库配置（例如 Redis、MongoDB、Jpa、RabbitMQ、Quartz 等等），Spring Boot 应用中这些第三方库几乎可以零配置的开箱即用。
>
> 简单来说就是SpringBoot其实不是什么新的框架，它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，spring boot整合了所有的框架 。
>
> Spring Boot 出生名门，从一开始就站在一个比较高的起点，又经过这几年的发展，生态足够完善，Spring Boot 已经当之无愧成为 Java 领域最热门的技术。

#### Spring Boot的主要优点：

- 为所有Spring开发者更快的入门
- **开箱即用**，提供各种默认配置来简化项目配置
- 内嵌式容器简化Web项目
- 没有冗余代码生成和XML配置的要求

真的很爽，接下来我们快速去体验开发个博客的感觉吧！



## 项目开发（spring-boot个人博客）

## 1. 技术介绍和建站准备

### 1.1 建站需要

#### 网站前端

> [Semantic UI框架](https://semantic-ui.com/)
>
> html5+css+Javascript(jQuery) 三件套

#### 网站后端

> [SpringBoot](https://spring.io/projects/spring-boot)
>
> [Mybatis3](https://mybatis.org/mybatis-3/zh/index.html) (持久化框架)
>
> [thymeleaf 模板](https://www.thymeleaf.org/)
>
> [MySQL](https://www.mysql.com/cn/)

#### 工具与环境

> [IDEA](https://www.jetbrains.com/idea/)（可以破解或者到官网申请学生特权）
>
> http://maven.apache.org/download.cgi
>
> [JDK8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
>
> [SQLyog](https://www.webyog.com/product/sqlyog)

### 1.2 需求与功能

#### 1.2.1 用户故事

用户故事是敏捷框架中的一种开发方法。可以帮助开发者转换视角，以用户的角度更好的把握需求，从而实现具有商业价值的功能。

>  用户故事最好是用户团队编写

**用户故事模板**：

-  As a (role of user), I want (some feature) so that (some business value).
-  作为一个(某个角色) 使用者，我可以做(某个功能) 事情，如此可以有(某个商业价值) 的好处

**关键点**：角色、功能、商业价值

**举例**：

-  作为一个招聘网站**注册用户**，我想**查看最近3天发布的招聘信息**，以便于**了解最新的招聘信息**。
-  作为公司，可以张贴新工作。



个人博客系统的用户故事：

角色：**普通访客**，**管理员（我）**

*  访客，可以分页查看所有的博客
*  访客，可以快速查看博客数最多的6个分类
*  访客，可以查看所有的分类
*  访客，可以查看某个分类下的博客列表
*  访客，可以快速查看标记博客最多的10个标签
*  访客，可以查看所有的标签
*  访客，可以查看某个标签下的博客列表
*  访客，可以根据年度时间线查看博客列表
*  访客，可以快速查看最新的推荐博客
*  访客，可以用关键字全局搜索博客
*  访客，可以查看单个博客内容
*  访客，可以对博客内容进行评论
*  访客，可以赞赏博客内容
*  访客，可以微信扫码阅读博客内容
*  访客，可以在首页扫描公众号二维码关注我
*  我，可以用户名和密码登录后台管理
*  我，可以管理博客
   *  我，可以发布新博客
   *  我，可以对博客进行分类
   *  我，可以对博客打标签
   *  我，可以修改博客
   *  我，可以删除博客
   *  我，可以根据标题，分类，标签查询博客
*  我，可以管理博客分类
   *  我，可以新增一个分类
   *  我，可以修改一个分类
   *  我，可以删除一个分类
   *  我，可以根据分类名称查询分类
*  我，可以管理标签
   *  我，可以新增一个标签
   *  我，可以修改一个标签
   *  我，可以删除一个标签
   *  我，可以根据名称查询标签



#### 1.2.2 功能规划

![image-20201104002704626]($%7Bstatic%7D/image-20201104002704626.png)



## 2. 页面设计与开发

### 2.1 设计

**页面规划：**

- 前端展示：首页、详情页、分类、标签、归档、关于我
- ![image-20201104143054333]($%7Bstatic%7D/image-20201104143054333.png)
- 后台管理：登录界面
- ![image-20201104151438979]($%7Bstatic%7D/image-20201104151438979.png)
- 管理员首页
- ![image-20201104151519511]($%7Bstatic%7D/image-20201104151519511.png)
- 管理员博客管理
- ![image-20201104151625521]($%7Bstatic%7D/image-20201104151625521.png)
- 管理员分类管理
- ![image-20201104151653991]($%7Bstatic%7D/image-20201104151653991.png)
- 管理员标签管理
- ![image-20201104151734043]($%7Bstatic%7D/image-20201104151734043.png)

### 2.1 页面开发

- [Semantic UI官网](https://semantic-ui.com/)
- [Semantic UI中文官网](http://www.semantic-ui.cn/)
- [Vscode](https://code.visualstudio.com/)
- [背景图片资源](https://www.toptal.com/designers/subtlepatterns/)

### 2.1 插件集成

- [编辑器 Markdown](https://pandao.github.io/editor.md/)
- [内容排版 typo.css](https://github.com/sofish/typo.css)
- [动画 animate.css](https://daneden.github.io/animate.css/)
- [代码高亮 prism](https://github.com/PrismJS/prism)
- [目录生成 Tocbot](https://tscanlin.github.io/tocbot/)
- [滚动侦测 waypoints](http://imakewebthings.com/waypoints/)
- [平滑滚动 jquery.scrollTo](https://github.com/flesler/jquery.scrollTo)
- [二维码生成 qrcode.js](https://davidshimjs.github.io/qrcodejs/)



## 3. 项目创建

> [IDEA下载 https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/) (专业版)
>
> 激活途径：
>
> - 官网申请学生免费激活
> - 搜索相关激活方法

Spring官方提供了非常方便的工具让我们快速构建应用

Spring Initializr：https://start.spring.io/

##### **项目创建方式一：**使用Spring Initializr 的 Web页面创建项目

1、打开  https://start.spring.io/

![image-20201104004812194]($%7Bstatic%7D/image-20201104004812194.png)

2、填写项目信息

![image-20201104005717808]($%7Bstatic%7D/image-20201104005717808.png)

3、点击”Generate Project“按钮生成项目；下载此项目

项目目录如下：

```xml
└─blog
    ├─.mvn
    │  └─wrapper
    └─src
        ├─main
        │  ├─java
        │  │  └─com
        │  │      └─blog
        │  └─resources
        └─test
            └─java
                └─com
                    └─blog
```



4、解压项目包，并用IDEA以Maven项目导入，一路下一步即可，直到项目导入完毕。

5、如果是第一次使用，可能速度会比较慢，包比较多、需要耐心等待一切就绪。



##### **项目创建方式二：**使用 IDEA 直接创建项目

1、创建一个新项目

2、选择spring initalizr ， 可以看到默认就是去官网的快速构建工具那里实现

![image-20201104010246918]($%7Bstatic%7D/image-20201104010246918.png)

3、填写项目信息

![image-20201104010331212]($%7Bstatic%7D/image-20201104010331212.png)

4、选择初始化的组件（可以不选，后期可以在pom.xml导入相应的包）

5、填写项目路径（随意填写，只要对应目录有足够空间存放即可）

![image-20201104010447567]($%7Bstatic%7D/image-20201104010447567.png)

6、等待项目构建成功

创建成功之后查看目录结构如下

```xml
├─.idea
├─.mvn
│  └─wrapper
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─blog
    │  └─resources
    └─test
        └─java
            └─com
                └─blog
```

**根据两个方式生成的项目录结构，可以发现两者基本是一样的，所以根据个人选择，个人推荐idea直接创建**





## 4. 数据库

### 数据库表设计

**t_blog表**

| 列名            | 实体属性类型 | 键          | 备注                    |
| --------------- | ------------ | ----------- | ----------------------- |
| id              | bigint       | primary key | auto_increment          |
| title           | varchar      |             | 题目                    |
| content         | text         |             | 内容                    |
| first_picture   | varchar      |             | 首图                    |
| flag            | varchar      |             | 文章标记 （原创，转载） |
| views           | int          |             | 默认为0                 |
| appreciation    | int          |             | 赞赏                    |
| share_statement | int          |             | 分享                    |
| commentabled    | int          |             | 评论                    |
| published       | int          |             | 版权                    |
| recommened      | int          |             | 推荐                    |
| create_time     | datetime     |             | 创建时间                |
| update_time     | datetime     |             | 更新时间                |
| type_id         | bigint       |             | 类型id                  |
| user_id         | bigint       |             | 用户id                  |
| description     | text         |             | 描述                    |
| tag_ids         | varchar      |             | 标签id                  |

**t_type表**

| 列名 | 实体属性类型 | 键          | 备注           |
| ---- | ------------ | ----------- | -------------- |
| id   | bigint       | primary key | Auto_increment |
| name | varchar      |             | 类型名称       |

**t_tag表**

| 列名 | 实体属性类型 | 键          | 备注           |
| ---- | ------------ | ----------- | -------------- |
| id   | bigint       | primary key | Auto_Increment |
| name | varchar      |             | 标签名称       |

**t_comment表**

| 列名              | 实体属性类型 | 键          | 备注           |
| ----------------- | ------------ | ----------- | -------------- |
| id                | bigint       | primary key | Auto Increment |
| nickname          | varchar      |             | 评论用户名称   |
| email             | varchar      |             | 邮箱地址       |
| content           | varchar      |             | 评论内容       |
| avatar            | varchar      |             | avatar用户图像 |
| create_time       | datetime     |             | 创建时间       |
| update_time       | datetime     |             | 更新时间       |
| parent_comment_id | bigint       |             | 父级评论id     |
| admincomment      | int          |             | 管理员判断     |

**t_user表**

| 列名        | 实体属性类型 | 键          | 备注       |
| ----------- | ------------ | ----------- | ---------- |
| id          | bigint       | primary key | 用户id     |
| nickname    | varchar      |             | 用户昵称   |
| username    | varchar      |             | 用户登录名 |
| password    | varchar      |             | 登录密码   |
| email       | varchar      |             | 邮箱地址   |
| avatar      | varchar      |             | 图像地址   |
| type        | int          |             | 类型       |
| create_time | datetime     |             | 创建时间   |
| update_time | datetime     |             | 更新时间   |

**t_blog_tags表**

| 列名    | 实体属性类型 | 键          | 备注           |
| ------- | ------------ | ----------- | -------------- |
| id      | bigint       | primary key | Auto Increment |
| tag_id  | bigint       |             | 标签id         |
| blog_id | bigint       |             | 博客id         |

**建表用到的sql语句**

```sql
#创建数据库
create database `blog`;

#创建t_blog表
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `first_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `views` int DEFAULT NULL,
  `appreciation` int NOT NULL DEFAULT '0',
  `share_statement` int NOT NULL DEFAULT '0',
  `commentabled` int NOT NULL DEFAULT '0',
  `published` int NOT NULL DEFAULT '0',
  `recommend` int NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `tag_ids` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE	#B-tree算法减少定位记录时所经历的中间过程,从而加快存取速度
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT; #数据库存储引擎选择InnoDB,字符类型选择utf-8,防止乱码

#创建t_blog_tags表
DROP TABLE IF EXISTS `t_blog_tags`;
CREATE TABLE `t_blog_tags` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tag_id` bigint DEFAULT NULL,
  `blog_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#创建t_comment表
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `blog_id` bigint DEFAULT NULL,
  `parent_comment_id` bigint DEFAULT NULL,
  `admincomment` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#创建t_tag表
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#创建t_type表
DROP TABLE IF EXISTS `t_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#创建t_user表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
```

到这一步，数据库的设计基本完成，接下来可以开始进行框架的构建



## 5. 框架构建



#### 5.1  引入依赖以及配置文件

##### **引入Spring Boot模块**

*  web
*  Thymeleaf
*  Mybatis
*  MySQL
*  Aspects
*  DevTools (热部署)

> 所谓热部署，就是在应用正在运行的时候升级软件，却不需要重新启动应用。
>
> 对于[Java](https://baike.baidu.com/item/Java/85979)应用程序来说，热部署就是在运行时更新Java类文件。在基于Java的[应用服务器](https://baike.baidu.com/item/应用服务器)实现热部署的过程中，类装入器扮演着重要的角色。大多数基于Java的应用服务器，包括[EJB](https://baike.baidu.com/item/EJB/144195)服务器和[Servlet](https://baike.baidu.com/item/Servlet/477555)容器，都支持热部署。类装入器不能重新装入一个已经装入的类，但只要使用一个新的类装入器实例，就可以将类再次装入一个正在运行的应用程序。

接下来便是在pom.xml里配置相关依赖（查找相关依赖可到[mvnrepository](https://mvnrepository.com/)查找，基本可以找到项目开发需要用到的各个版本的模块）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com</groupId>
    <artifactId>blog</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>blog</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <!--这三个jar包作用是将markdown格式转成html格式-->
        <dependency>
            <groupId>com.atlassian.commonmark</groupId>
            <artifactId>commonmark</artifactId>
            <version>0.10.0</version>
        </dependency>

        <dependency>
            <groupId>com.atlassian.commonmark</groupId>
            <artifactId>commonmark-ext-heading-anchor</artifactId>
            <version>0.10.0</version>
        </dependency>
        <dependency>
            <groupId>com.atlassian.commonmark</groupId>
            <artifactId>commonmark-ext-gfm-tables</artifactId>
            <version>0.10.0</version>
        </dependency>
        <!--markdown格式转成html格式模块引入结束-->
        <!--配置spring-boot启动器-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.13</version>
        </dependency>
        <!--配置AOP模块-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <!--配置thyleaf模板-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!--配置web模块-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--配置mybatis模块，方便后面数据持久化，可以更好的与数据接口形成映射-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.1</version>
        </dependency>
        <!--引入devtools模块-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <!--引入mysql驱动包-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!--引入lombok模块，可以减少代码冗余，可以减少代码量，高效开发-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!--引入spring-boot-starter-test测试模块，其中包含junit测试框架,可以进行单元，切片，功能测试-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
		<!--导入junit依赖，进行单元测试要用到-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <!--这个地方可以理解为每个spring-boot必备部件，使得能够以Maven的方式为应用提供Spring-Boot的支持-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

##### **配置application.yml**

**注意：spring-boot项目默认配置文件为.preperties格式，这里选择yml的原因是配置起来更加方便，以及代码的层次更加清晰**

```yaml
#springboot整合了两个Web容器（Tomcat,Jetty），默认使用的Web容器为Tomcat,端口为8080
server:	#自定义服务端口号
  port: 10086

spring:            #全局配置
  thymeleaf:		
    cache: false	#thymeleaf缓存配置, 默认为true,即修改了html需要重启, 这样配置可以让我们服务期间作出修改可以在前端快速得到响应，方便开发模式。
  profiles:			# 这里可以选择对应环境
    active: dev		# 开发环境
    # active: pro	# 生产环境

comment.avatar: /images/avatar.jpg
```

**接下来便是配置全局配置里对应的两个环境的application**

```yaml
# 对应开发环境
# filename: application-dev.yml

# 数据库连接配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8  #注意时区设置，不然会连接失败
    driver-class-name: com.mysql.cj.jdbc.Driver 	# Mysql-5版本可以用 com.mysql.jdbc.Driver
    username: "本机数据库用户名"
    password: "本机数据库密码"


pagehelper:                #分页插件
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
  params:

#Mybatis配置
mybatis:
  type-aliases-package: com.blog.pojo   #设置别名
  mapper-locations: classpath:mapper/*.xml   #ָ指定myBatis的核心配置文件与Mapper映射文件

# 日志配置
logging:  #日志级别
  level:
    root: info
    com.blog: debug
  file:
    path: log/blog-dev.log
  #开发环境
```

```yaml
# 对应生产环境
# filename: application-pro.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8 
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: "服务器数据库用户名"
    password: "服务器数据库密码"

pagehelper:                #分页插件
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
  params:

mybatis:
  type-aliases-package: com.blog.pojo   #设置别名
  mapper-locations: classpath:mapper/*.xml   #ָ指定myBatis的核心配置文件与Mapper映射文件

logging:  #日志级别
  level:
    root: warn
    com.blog: info
  file:
    path: log/blog-pro.log
 #生产环境
```

**注意：在这一步中，关于引用到的相关框架的配置，最好的方法就是打开对应官方文档的网站，根据自己的需要，阅读相应的介绍，跟着官方文档的介绍的步骤走，能减少踩坑的几率，当然，阅读官方文档其实也能学到很多新的东西。**

#### 5.2 异常处理

##### 1.定义错误页面

- 404
- 500
- error

##### 2.全局处理异常

```java
@ControllerAdvice   //拦截所有controller抛出的异常，对异常进行统一的处理
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)  //表示该方法可以处理所有类型异常
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {

        //日志打印异常信息
        logger.error("Request url: {}, Exception: {}", request.getRequestURI(), e);

        //不处理带有ResponseStatus注解的异常
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        //返回异常信息到自定义error页面
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("Exception", e);
        mv.setViewName("error/error");	// 跳转到指定错误页面

        return mv;
    }
}
```



#### 5.3 拦截器的配置

##### webConfig拦截器的设置

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {   //配置拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")       // 对于admin目录下的子目录的子目录..设置拦截
                .excludePathPatterns("/admin")      // 对于admin同目录下的文件设置拦截
                .excludePathPatterns("/admin/login");   // 对admin/login设置拦截无法直接访问
    }
}
```



#### 5.4 日志处理

##### 1. 记录日志内容

- **请求URL**

  ```java
  String url = request.getRequestURL().toString();
  ```

- **访问者IP**

  ```java
  String ip = request.getRemoteAddr();
  ```

- **调用方法 ClassMethod**

  ```java
  String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
  ```

- **参数 args**

  ```java
  Object[] args = joinPoint.getArgs();
  ```

- **返回内容**

  ```java
  // 通过注解方式获得返回值
  @AfterReturning(returning = "result", pointcut = "log()")
  public void doAfterReturn(Object result){
      //打印返回值
      logger.info("Result: {}", result);
  }
  ```

##### 2. 接口日志记录AOP实现

```java
@Aspect    //定义切面
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.blog.controller.*.*(..))")        //定义切入点表达式
    public void log(){}

    @Before("log()")    //引用切入点
    public void doBefore(JoinPoint joinPoint){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获得请求链接，IP地址
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获得类名.方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获得方法参数
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        //打印请求信息
        logger.info("Request: {}", requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("------------doAfter------------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){

        //打印返回值
        logger.info("Result: {}", result);
    }

    // lombok 注解
    @Data   // 注解在类上；提供类所有属性的get和set方法，此外还提供了equals、canEqual、hashCode、toString 方法
    @AllArgsConstructor // 注解在类上；为类提供一个全参的构造方法，加了这个注解后，类中不提供默认构造方法了
    public class RequestLog{      //用于封装请求信息
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
```

**在很多时候，我们在开发一个测试框架时，不管出于何种考虑，比如调试的角度，一般都会有个全局记录日志的模块功能。此模块一般上会记录每个对数据有进行变更的操作记录，若是在web测试平台上，还会记录请求的url,请求的IP,及当前的操作人，操作的方法说明等等。在很多时候，我们需要记录请求的参数信息时，通常是利用拦截器、过滤器或者 AOP 等来进行统一拦截。**

**注意：日志记录不能影响正常的方法请求**

#### 5.5 页面处理

##### 1.静态资源的导入

- 前端页面 （添加到template目录下，spring-boot有个@ImportResource()注解将静态资源导入到Spring容器中）

  ```java
  // 启动类@SpringBootApplication>@EnableAutoConfiguration>@Import({AutoConfigurationImportSelector.class})>ResourceLoaderAware>ResourceLoader>CLASSPATH_URL_PREFIX = "classpath:";
  // 配置文件配置与优先级(从高到低，高优先级会覆盖低优先级)
  file: ./config/
  file: ./
  classpath: /config/
  classpath: /
  ```

- 静态资源 （包括集成插件，css样式，图片资源...）

##### 2. thymeleaf布局

* 定义fragment (将每一个页面公用的部分提取出来，写到_fragment.html文件中)

  哪些是共用部分呢？(其实在我们设计的这个网页样式中除了中间部件不一样之外，其它的例如头部，底部，集成插件等在不同页面中很多都是一样的)。

  如下所示：

  1. 共用head标签里的样式文件

  2. 头部导航栏

     ![image-20201104170101820]($%7Bstatic%7D/image-20201104170101820.png)

  3. 底部共用块

     ![image-20201104165819667]($%7Bstatic%7D/image-20201104165819667.png)

  4. 共用script脚本

  **以下为提取html代码示例：**

  1. 提取head标签里的公用部分

     ```html
     <head th:fragment="head(title)">   <!--title为传参-->
       <meta charset="UTF-8">
       <meta name="viewport" content="width=device-width, initial-scale=1.0">
       <title th:replace="${title}">博客详情</title>
       <link rel="stylesheet" href="https://cdn.jsdelivr.net/semantic-ui/2.2.4/semantic.min.css">
       <link rel="stylesheet" href="../static/css/typo.css" th:href="@{/css/typo.css}">
       <link rel="stylesheet" href="../static/css/animate.css" th:href="@{/css/animate.css}">
       <link rel="stylesheet" href="../static/lib/prism/prism.css" th:href="@{/lib/prism/prism.css}">
       <link rel="stylesheet" href="../static/lib/tocbot/tocbot.css" th:href="@{/lib/tocbot/tocbot.css}">
       <link rel="stylesheet" href="../static/css/me.css" th:href="@{/css/me.css}">
       <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome/css/font-awesome.min.css">
       <script src="https://cdn.jsdelivr.net/gh/stevenjoezhang/live2d-widget@latest/autoload.js"></script>
     </head>
     ```

  2. 提取头部导航栏的公用部分

     ```html
     <!--头部导航-->
     <nav th:fragment="menu(n)" class="ui attached segment m-padded-tb-mini m-shadow-small" >
       <div class="ui container">
         <div class="ui secondary stackable menu">
           <h1 class="ui pink header item">君の名は</h1>
           <a href="#" th:href="@{/}" class="m-item item blue m-mobile-hide" th:classappend="${n==1}?'active'"><i class=" home icon"></i>首页</a>
           <a href="#" th:href="@{/types/-1}" class="m-item violet item m-mobile-hide" th:classappend="${n==2}?'active'"><i class=" idea icon"></i>分类</a>
           <a href="#" th:href="@{/tags/-1}" class="m-item pink item m-mobile-hide" th:classappend="${n==3}?'active'"><i class=" tags icon"></i>标签</a>
           <a href="#" th:href="@{/archives}"class="m-item orange item m-mobile-hide" th:classappend="${n==4}?'active'"><i class=" clone icon"></i>时间线</a>
           <a href="#" th:href="@{/about}"class="m-item green item m-mobile-hide" th:classappend="${n==5}?'active'"><i class=" info icon"></i>关于我</a>
           <div class="right m-item item m-mobile-hide">
             <form name="search" action="#" method="post" th:action="@{/search}" target="_blank">
               <div class="ui icon  transparent input m-margin-tb-tiny">
                 <input type="text" name="query" placeholder="Search...." th:value="${query}">
                 <i onclick="document.forms['search'].submit()" class="search link icon"></i>
               </div>
             </form>
           </div>
         </div>
       </div>
       <a href="#" class="ui menu toggle black icon button m-right-top m-mobile-show">
         <i class="sidebar icon"></i>
       </a>
     </nav>
     
     <!--导航-->
     ```

  3. 提取底部导航的公用部分

     ```html
     <!--底部footer-->
     <footer th:fragment="footer" class="ui inverted vertical segment m-padded-tb-massive">
       <div class="ui center aligned container">
         <div class="ui inverted divided stackable grid">
     
           <div class="three wide column">
             <div class="ui inverted link list">
               <div class="item">
                 <img src="../static/images/wechat.jpg" th:src="@{/images/wechat.jpg}"  class="ui rounded image" alt="" style="width: 110px">
               </div>
             </div>
           </div>
     
           <div class="three wide column">
             <h4 class="ui inverted header m-text-thin m-text-spaced ">联系我</h4>
             <div class="ui inverted link list">
               <a href="#" class="item m-text-thin">Email：1194840395@qq.com</a>
               <a href="#" class="item m-text-thin">QQ：1194840395</a>
             </div>
           </div>
     
           <div class="ten wide column">
             <h4 class="ui inverted header m-text-thin m-text-spaced ">临江仙·梦后楼台高锁</h4>
             <p class="m-text-thin m-text-spaced m-opacity-mini">
               梦后楼台高锁，酒醒帘幕低垂。去年春恨却来时。落花人独立，微雨燕双飞。</p>
             <p class="m-text-thin m-text-spaced m-opacity-mini">
               记得小苹初见，两重心字罗衣。琵琶弦上说相思。当时明月在，曾照彩云归。</p>
           </div>
         </div>
         <div class="ui inverted section divider"></div>
         <p class="m-text-thin m-text-spaced m-opacity-tiny">Copyright © 2020 </p>
       </div>
     
     </footer>
     ```

  4. 提取页面共用的script脚本

     ```html
     <th:block th:fragment="script">
       <script src="https://cdn.jsdelivr.net/npm/jquery@3.2/dist/jquery.min.js"></script>
       <script src="https://cdn.jsdelivr.net/semantic-ui/2.2.4/semantic.min.js"></script>
       <script src="//cdn.jsdelivr.net/npm/jquery.scrollto@2.1.2/jquery.scrollTo.min.js"></script>
     
       <script src="../static/lib/prism/prism.js" th:src="@{/lib/prism/prism.js}"></script>
       <script src="../static/lib/tocbot/tocbot.min.js" th:src="@{/lib/tocbot/tocbot.min.js}"></script>
       <script src="../static/lib/qrcode/qrcode.min.js" th:src="@{/lib/qrcode/qrcode.min.js}"></script>
       <script src="../static/lib/waypoints/jquery.waypoints.min.js" th:src="@{/lib/waypoints/jquery.waypoints.min.js}"></script>
     </th:block>
     ```

* 使用fragment布局

以blog页面为示例，引用thymeleaf模块对_fragment.html定义好对应模块,调加到页面中对应的位置，既可以实现thymeleaf定义好的内容进行完美嵌入，这里就展示**blog.html嵌入__fragment部分**，由于代码篇幅过长，这里就不不详细展示了。

```html
<!--head标签-->
<head  th:replace="_fragments :: head(~{::title})">
    <title>博客详情</title>
</head>
<!--头部导航-->
<nav th:replace="_fragments :: menu(0)" ></nav>
<!--底部footer-->
<footer th:replace="_fragments :: footer" class="ui inverted vertical segment m-padded-tb-massive"></footer>
<!--导入的script-->
<th:block th:replace="_fragments :: script">

</th:block>
```

另外为了让每个页面都可以用thymeleaf模板进行渲染，需要再文件头部加上

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org"> 
```

**很重要，因为这是一个命令空间，将静态页面转换为动态的视图，需要进行动态处理的元素将使用“th:”前缀**

##### 3. 错误页面美化

页面效果如下图：

![image-20201104171052197]($%7Bstatic%7D/image-20201104171052197.png)

html代码如下：

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head th:replace="_fragments :: head(~{::title})">
    <title>404</title>
</head>

<body>

<!--头部导航-->
<nav  th:replace="_fragments :: menu(0)" ></nav>

<br>
<br>
<br>

<div class="m-container-small m-padded-tb-massive">
    <div class="ui error message m-padded-tb-huge" >
        <div class="ui contianer">
            <h2>404</h2>
            <p>对不起，你访问的资源不存在</p>
        </div>
    </div>
</div>
<br>
<br>
<br>
<br>

<!--底部footer-->
<footer th:replace="_fragments :: footer" class="ui inverted vertical segment m-padded-tb-massive"></footer>

</body>
</html>
```

#### 5.6 工具类的导入

- MarkdownUtils（可以对博客内容进行格式转换）

```java
public class MarkdownUtils {

    /**
     * markdown格式转换成HTML格式
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }


    public static void main(String[] args) {
        String table = "| 芜湖起飞 | LOL   | AWSL   |\n" +
                "| ----- | ---- | ----- |\n" +
                "| LPL  | SKT  | LCL    |\n" +
                "| 永远滴神  | YYDS | 联动 |\n" +
                "\n";
        String a = "[imCoding 爱编程](http://www.lirenmi.cn)";
        System.out.println(markdownToHtmlExtensions(a));
    }
}
```

- MD5Utils（Message-Digest Algorithm5）加密工具，让我们的用户数据更加安全，同时增加网站安全性。

  数据库存储的用户密码是经过MD5加密过的明文密码，当用户在前端页面输入用户密码时，后端通过这个工具类对密码字段进行加密之后，与我们数据库存储的对应用户的密码进行比较，若两者相同，则通过检测，否则检测不过。

```java
public class MD5Utils {
    /**
     * MD5加密类
     * @param str 要加密的字符串
     * @return    加密后的字符串
     */
    public static String code(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        //System.out.println(code("******"));
    }
}
```

## 6. 应用分层

<img src="$%7Bstatic%7D/image-20201104200048093.png" alt="image-20201104200048093" style="zoom:80%;" />

在这个项目开发中，ORM选择了MybatIs, 以下是这几层主要负责的功能：

- POJO层：封装数据，如果你要封装到对应的POJO层，一定要保证字段和POJO中的变量名一样，如果不一样，无法封装。
- DAO层：数据接口的定义，比如经典的CRUD各自的单独操作，另外还有一些根据需求自己定义的接口。（Mybatis 也是通过xml文件的映射，以一种更为有效方便的方式编写语句然后通过数据库来对这些接口需求的CRUD进行实现）。
- Service:  业务逻辑层主要负责我们网站的主要业务，它负责调用DAO层接口，接收DAO层返回的数据负责最重要的业务。
- controller层：负责请求和响应控制，负责前后端交互，接受前端请求，调用service层，接收service层返回的数据，最后返回具体的页面和数据到客户端。

到这里整个项目基本开发流程已经确定，接下来需要开始实现每一层的功能，让它们有机地组合起来，从而让博客网站正常有序地运行。



## 7. POJO, DAO, Service三层设计

**概念：**

- **PO：持久对象 (persistent object)，po(persistent object)就是在Object/Relation Mapping框架中的Entity，po的每个属性基本上都对应数据库表里面的某个字段。完全是一个符合Java Bean规范的纯Java对象，没有增加别的属性和方法。持久对象是由insert数据库创建，由数据库delete删除的。基本上持久对象生命周期和数据库密切相关。**

- **POJO：POJO（Plain Ordinary Java Object）简单的Java对象，实际就是普通JavaBeans，是为了避免和EJB混淆所创造的简称。通指没有使用Entity Beans的普通java对象，可以把POJO作为支持业务逻辑的协助类。**

  **POJO实质上可以理解为简单的实体类，顾名思义POJO类的作用是方便程序员使用数据库中的数据表，对于广大的程序员，可以很方便的将POJO类当做对象来进行使用，当然也是可以方便的调用其get,set方法。POJO类也给我们在struts框架中的配置带来了很大的方便。一个POJO持久化以后就是PO，直接用它传递、传递过程中就是DTO**

- **DAO: 数据访问对象是第一个面向对象的数据库接口**，**是一个数据访问接口(Data Access Object)。它可以把POJO持久化为PO，用PO组装出来VO、DTO。**

  **DAO模式是标准的J2EE设计模式之一.开发人员使用这个模式把底层的数据访问操作和上层的商务逻辑分开.一个典型的DAO实现有下列几个组件：**

  **1. 一个DAO工厂类；**

  **2. 一个DAO接口；**

  **3. 一个实现DAO接口的具体类；**

  **4. 数据传递对象（有些时候叫做值对象）**

  **具体的DAO类包含了从特定的数据源访问数据的逻辑，一般一个DAO类和一张表对应，每个操作要和事务关联。**

- ##### Service层：业务层 控制业务

  **Service层主要负责业务模块的逻辑应用设计。先设计放接口的类，再创建实现的类，然后在配置文件中进行配置其实现的关联。service层调用dao层接口，接收dao层返回的数据，完成项目的基本功能设计。**

  **封装Service层的业务逻辑有利于业务逻辑的独立性和重复利用性。**

### 1. 实体类设计（POJO层又名Model层）

- 博客	Blog
- 博客分类Type
- 博客评论 Tag
- 博客评论 Comment
- 用户表 User
- 博客标签 BlogAndTag

**实体关系：**

![image-20201104141602592]($%7Bstatic%7D/image-20201104141602592.png)

**评论类自关联关系：**

![image-20201104141638229]($%7Bstatic%7D/image-20201104141638229.png)

**Blog类：**

![image-20201104141725287]($%7Bstatic%7D/image-20201104141725287.png)

**Type类：**

![image-20201104141752253]($%7Bstatic%7D/image-20201104141752253.png)

**Tag类：**

![image-20201104141817545]($%7Bstatic%7D/image-20201104141817545.png)

**Comment类：**

![image-20201104141840742]($%7Bstatic%7D/image-20201104141840742.png)

**User类：**

![image-20201104141900660]($%7Bstatic%7D/image-20201104141900660.png)

Blog实体类的实现

```java
// 通过lombok插件，在我们编写代码时不需要自己编写get方法，和构造方法，提高编写效率
// 它会在经过编译之后生成的.class文件中自动注入这些方法
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;

    //这个属性用来在mybatis中进行连接查询的
    private Long typeId;
    private Long userId;

    //获取多个标签的id
    private String tagIds;
    private String description;
    private Type type;
    private User user;
    private List<Tag> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    //将tags集合转换为tagIds字符串形式：“1,2,3”,用于编辑博客时显示博客的tag
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag: tags){
                if(flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }
}
```

#### BlogAndTag实体类的实现

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAndTag {

    private Long tagId;
    private Long blogId;
}
```

#### Comment实体类的实现

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private boolean adminComment;  //是否为管理员评论

    //头像
    private String avatar;
    private Date createTime;

    private Long blogId;
    private Long parentCommentId;  //父评论id
    private String parentNickname;

    //回复评论
    //private List<Comment> replyComments = new ArrayList<>();

    //父评论
    private Comment parentComment;
    private Blog blog;
}
```

#### Tag实体类的实现

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;
    private List<Blog> blogs = new ArrayList<>();
}
```

#### Type实体类的实现

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    private Long id;
    private String name;
    private List<Blog> blogs = new ArrayList<>();
}
```

#### User实体类的实现

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type;
    private Date createTime;
    private Date updateTime;
    private List<Blog> blogs = new ArrayList<>();
}
```

### 2. 方法类设计（DAO层）

#### BlogDao实现

```java
@Mapper		// 使用Spring 管理 Mybatis 操作接口
@Repository	// 这里Repository可以去掉，但为了不让Autowire绑定时对象爆红(与spring默认的装配方式有关)，加上也无所谓
public interface BlogDao {
    Blog getBlog(Long id);  //后台展示博客
    Blog getDetailedBlog(@Param("id") Long id);  //博客详情
    List<Blog> getAllBlog();            // 获取所有博客
    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客
    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客
    List<Blog> getIndexBlog();  //主页博客展示
    List<Blog> getAllRecommendBlog();  //推荐博客展示
    List<Blog> getSearchBlog(String query);  //全局搜索博客
    List<Blog> searchAllBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客
    List<String> findGroupYear();  //查询所有年份，返回一个集合
    List<Blog> findByYear(@Param("year") String year);  //按年份查询博客

    int saveBlog(Blog blog);
    int saveBlogAndTag(BlogAndTag blogAndTag);
    int updateBlog(Blog blog);
    int deleteBlog(Long id);
}
```

#### CommentDao实现

```java
@Mapper
@Repository
public interface CommentDao {
    //根据创建时间倒序来排
    List<Comment> findByBlogIdAndParentCommentNull(@Param("blogId") Long blogId, @Param("blogParentId") Long blogParentId);
    //查询父级对象
    Comment findByParentCommentId(@Param("parentCommentId")Long parentcommentid);
    //添加一个评论
    int saveComment(Comment comment);
}
```

#### TagDao实现

```java
@Mapper
@Repository
public interface TagDao {
    int saveTag(Tag tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    List<Tag> getAllTag();
    List<Tag> getBlogTag();  //首页展示博客标签
    int updateTag(Tag tag);
    int deleteTag(Long id);
}
```

#### TypeDao实现

```java
@Mapper
@Repository
public interface TypeDao {
    int saveType(Type type);
    Type getType(Long id);
    Type getTypeByName(String name);
    List<Type> getAllType();
    List<Type> getBlogType();  //首页右侧展示type对应的博客数量
    int updateType(Type type);
    int deleteType(Long id);
}
```

#### UserDao实现

```java
@Mapper
@Repository
public interface UserDao {
    User queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
```



#### Mybatis 对于DAO层接口的映射

对于与Mybatis 映射器相关的*Mapper.xml文件该如何去编写呢，这里还需要熟悉一下Mybatis相关语法，这里我们以编写BlogMapper.xml文件为例进行解释。

```xml
<!--由于我们把xml文件拆分成多个，因此每个*mapper.xml文件只用了一个映射器，当需要使用多个映射器时，可以用<mappers>标签来进行配置。-->
<!--映射器所要映射的命名空间(namespace)，也就是我们所要实现的Dao层的接口了，典型的面向接口编程。-->
<mapper namespace="com.blog.dao.BlogDao"> 
```

紧接我们需要根据接口所对应的实体类进行字段映射

```xml
<ResultMap id="blog" type="Blog">...</ResultMap>
<!--(...)中的内容即为我们接下来需要定义的对应实体类中包含的字段-->
```

再接下来我们需要定义（...）里面的内容

这里有三个概念:

- 当我们只需要单独定义一个实体类时，我们可以一个<result>标签来对每一个字段进行标注

- 当我们当前定义实体类中包含有其他实体类中的元素时，我们想的可能就是通过这个元素再去关联另一个实体类更多的字段信息，

  这是我们的<asociation>就登场了。

  打个比方：假如我们当前在一个qq群，我们就是这个群的一份子（也就是实体类某一个字段）, 而我们又属于另外一个qq群的一员，这时候，作为一个共享信息的地方，是不是需要通过联立两个群的信息资源以此来获得更多的信息，从而得到更大程度的信息共享<asociation>也是这个道理。

- 当我们当前定义的实体类在某个字段的属性在其他类型中被标记的多个值时，这个时候我们就需要用<collection>来进行定义

  打个比方：假如我们当前定义的是People类，这时候这个类有中有一个字段，比方说L_Sport(喜欢的运动)，这个时候就可能有多个了(打篮球，跑步...)，这个时候我们是不是就需要用一个集合来对这个字段所包含的这些值来进行描述

然后贴上<ResultMap>标签里(...)的内容

```xml
		<id property="id" column="id"/>
        <result property="title" column="title"/>
        <result property="content" column="content"/>
        <result property="flag" column="flag"/>
        <result property="views" column="views"/>
        <result property="updateTime" column="update_time"/>
        <result property="typeId" column="type_id"/>
        <result property="firstPicture" column="first_picture"/>
        <result property="shareStatement" column="share_statement"/>
        <result property="published" column="published"/>
        <result property="appreciation" column="appreciation"/>
        <result property="commentabled" column="commentabled"/>
        <result property="description" column="description"/>
        <result property="recommend" column="recommend"/>
        <result property="createTime" column="create_time"/>
        <result property="typeId" column="type_id"/>
        <result property="userId" column="user_id"/>
        <result property="tagIds" column="tag_ids"/>
        <association property="type" javaType="Type">
            <id property="id" column="typeid"/>
            <result property="name" column="typename"/>
        </association>
        <association property="user" javaType="User">
            <id property="id" column="uid"/>
            <result property="nickname" column="nickname"/>
            <result property="username" column="username"/>
            <result property="email" column="email"/>
            <result property="avatar" column="avatar"/>
        </association>
        <collection property="tags" ofType="Tag">
            <id property="id" column="tagid"/>
            <result property="name" column="tagname"/>
        </collection>
```

**以上部分就是这个实体类的数据结构，都定义好了之后，可以开始利用这些东西来进行一些方法的实现了。**



#### CRUD 必不可少

```xml
	<!--删除操作-->
	<delete id="deleteBlog">
     	delete from t_blog where id = #{id} 
	</delete>
```

```xml
    <!--查询操作-->
    <select id="getIndexBlog" resultMap="blog">  /*主页博客展示*/
        select b.id, b.title, b.first_picture, b.views, b.update_time, b.description,
        t.name typename, t.id typeid,
        u.nickname, u.avatar
        from t_blog b, t_type t, t_user u
        where b.type_id = t.id and  u.id = b.user_id order by b.update_time desc
    </select>
```

```xml
	<!--更新操作-->
    <update id="updateBlog" parameterType="Blog">
        update t_blog set published = #{published},flag = #{flag} ,
        title = #{title}, content = #{content}, type_id = #{typeId}, tag_ids = #{tagIds},
        first_picture = #{firstPicture} , description = #{description} , recommend = #{recommend} ,
        share_statement = #{shareStatement}, appreciation = #{appreciation},
        commentabled = #{commentabled} ,update_time = #{updateTime} where id = #{id};
    </update>
```

```xml
 	<!--插入操作-->
    <insert id="saveBlog" parameterType="Blog" useGeneratedKeys="true" keyProperty="id">
        insert into t_blog (title, content, first_picture, flag,
        views, appreciation, share_statement, commentabled,published,
        recommend, create_time, update_time, type_id, tag_ids, user_id, description)
        values (#{title}, #{content}, #{firstPicture}, #{flag}, #{views}, #{appreciation},
        #{shareStatement}, #{commentabled}, #{published}, #{recommend}, #{createTime},
        #{updateTime}, #{typeId}, #{tagIds}, #{userId}, #{description});
    </insert>
```

**以上便是我们通过*mapper.xml的编写实现接口的绑定 > 字段的定义 > 方法的实现（代码篇幅过长，只展示了CRUD操作）。因此，通过*mapper.xml的编写，我们就不用再去实现接口了，Mybatis可以通过执行指定的sql帮我们实现我们需要的接口。**



### 3. 服务层设计（Service）

#### BlogService接口定义

```java
public interface BlogService {

    Blog getBlog(Long id);  //后台展示博客
    Blog getDetailedBlog(Long id);  //前端展示博客
    List<Blog> getAllBlog();    // 获取所有博客
    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客
    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客
    List<Blog> getIndexBlog();  //主页博客展示
    List<Blog> getAllRecommendBlog();  //推荐博客展示
    List<Blog> getSearchBlog(String query);  //全局搜索博客
    Map<String,List<Blog>> archiveBlog();  //归档博客
    int countBlog();  //查询博客条数
    int saveBlog(Blog blog);       // 保存剥壳
    int updateBlog(Blog blog);      // 更新博客
    int deleteBlog(Long id);        // 删除博客
    List<Blog> searchAllBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客

}
```

#### BlogServiceImpl接口类的实现

```java
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogDao blogDao;

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getBlog(id);
    }

    @Override
    public Blog getDetailedBlog(Long id) {
        Blog blog = blogDao.getDetailedBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        return blog;
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogDao.getAllBlog();
    }

    @Override
    public List<Blog> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getByTagId(Long tagId) {
        return blogDao.getByTagId(tagId);
    }

    @Override
    public List<Blog> getIndexBlog() {
        return blogDao.getIndexBlog();
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogDao.getAllRecommendBlog();
    }

    @Override
    public List<Blog> getSearchBlog(String query) {
        return blogDao.getSearchBlog(query);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        Set<String> set = new HashSet<>(years);  //set去掉重复的年份
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : set) {
            map.put(year, blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public int countBlog() {
        return blogDao.getAllBlog().size();
    }

    @Override
    public List<Blog> searchAllBlog(Blog blog) {
        return blogDao.searchAllBlog(blog);
    }


    @Override    //新增博客
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        blogDao.saveBlog(blog);
        //保存博客后才能获取自增的id
        Long id = blog.getId();
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            //新增时无法获取自增的id,在mybatis里修改
            blogAndTag = new BlogAndTag(tag.getId(), id);
            blogDao.saveBlogAndTag(blogAndTag);
        }
        return 1;
    }

    @Override   //编辑博客
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
            blogDao.saveBlogAndTag(blogAndTag);
        }
        return blogDao.updateBlog(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        return blogDao.deleteBlog(id);
    }

}
```

#### CommentService接口定义

```java
public interface CommentService {
    List<Comment> getCommentByBlogId(Long blogId);
    int saveComment(Comment comment);
}
```

#### CommentServiceImpl接口类的实现

```java
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;

    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {  //查询父评论
        //没有父节点的默认为-1
        List<Comment> comments = commentDao.findByBlogIdAndParentCommentNull(blogId, Long.parseLong("-1"));
        return comments;
    }

    @Override
    //接收回复的表单
    public int saveComment(Comment comment) {
        //获得父id
        Long parentCommentId = comment.getParentComment().getId();
        //没有父级评论默认是-1
        if (parentCommentId != -1) {
            //有父级评论
            comment.setParentComment(commentDao.findByParentCommentId(comment.getParentCommentId()));
        } else {
            //没有父级评论
            comment.setParentCommentId((long) -1);
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.saveComment(comment);
    }

}
```

#### TagService接口定义

```java
public interface TagService {

    int saveTag(Tag tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    List<Tag> getAllTag();
    List<Tag> getBlogTag();  //首页展示博客标签
    List<Tag> getTagByString(String text);   //从字符串中获取tag集合
    int updateTag(Tag tag);
    int deleteTag(Long id);
}
```

#### TagServiceImpl接口类实现

```java
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.getAllTag();
    }

    @Override
    public List<Tag> getBlogTag() {
        return tagDao.getBlogTag();
    }

    @Override
    public List<Tag> getTagByString(String text) {    //从tagIds字符串中获取id，根据id获取tag集合
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(text);
        for (Long long1 : longs) {
            tags.add(tagDao.getTag(long1));
        }
        return tags;
    }

    private List<Long> convertToList(String ids) {  //把前端的tagIds字符串转换为list集合
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Override
    public int deleteTag(Long id) {
        return tagDao.deleteTag(id);
    }
}
```

#### TypeService接口定义

```java
public interface TypeService {

    int saveType(Type type);
    Type getType(Long id);
    Type getTypeByName(String name);
    List<Type> getAllType();
    List<Type> getBlogType();  //首页右侧展示type对应的博客数量
    int updateType(Type type);
    int deleteType(Long id);
}
```

#### TypeServiceImpl接口类实现

```java
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    //事务注解: Transaction只能应用到public方法上才有效，要正确使用Transaction注解，规避失效情况
    //这里使用默认配置，根据事务执行的状态来判断是否发生回滚，当抛出异常是，数据不保存到数据库，下面也是这个道理
    @Transactional
    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeDao.getType(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Transactional
    @Override
    public List<Type> getAllType() {
        return typeDao.getAllType();
    }


    @Override
    public List<Type> getBlogType() {
        return typeDao.getBlogType();
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeDao.deleteType(id);
    }
}
```

#### UserService接口定义

```java
public interface UserService {
    public User checkUser(String username, String password);
}
```

#### UserService接口类实现

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.queryByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
```



## 8. Controller 层设计

### 前言

------

Spring MVC 是构建在 Servlet API 上的原生框架，并从一开始就包含在 Spring 框架中。

#### Web MVC 架构及分析

MVC 三层架构如图所示，红色字体代表核心模块。其中 MVC 各分层分别为：

![image-20201105193258318]($%7Bstatic%7D/image-20201105193258318.png)

- Model （模型层）处理核心业务（数据）逻辑，模型对象负责在数据库中存取数据。这里的“数据”不仅限于数据本身，还包括处理数据的逻辑。
- View（视图层）用于展示数据，通常数据依据模型数据创建。
- Controller（控制器层）用于处理用户输入请求和响应输出，从试图读取数据，控制用户输入，并向模型发送数据。Controller 是在 Model 和 View 之间双向传递数据的中间协调者。

#### Spring MVC 架构及分析

------

Spring MVC 处理一个 HTTP 请求的流程，如图所示：

![image-20201105193339416]($%7Bstatic%7D/image-20201105193339416.png)

**整个过程详细介绍：**

1. 用户发送请求至前端控制器 DispatcherServlet
2. DispatcherServlet 收到请求调用处理器映射器 HandlerMapping
3. 处理器映射器根据请求 URL 找到具体的 Controller 处理器返回给 DispatcherServlet
4. DispatcherServlet 通过处理器适配器 HandlerAdapter 调用 Controller 处理请求
5. 执行 Controller 处理器的方法
6. Controller 执行完成返回 ModelAndView
7. HandlerAdapter 将 Controller 执行结果 ModelAndView 返回给 DispatcherServlet
8. DispatcherServlet 将 ModelAndView 的 ViewName 传给视图解析器 ViewReslover
9. ViewReslover 解析后返回具体的视图 View
10. DispatcherServlet 传递 Model 数据给 View，对 View 进行渲染（即将模型数据填充至视图中）
11. DispatcherServlet 响应用户



在开始讲解功能实现还需要再了解几个概念（选看）

#### URI vs URL

URI：(Uniform Resource Identifier 统一资源标识符)。

URL：(Uniform/Universal Resource Locator 统一资源定位符)。

关系：

- URI 属于 URL 更低层次的抽象，一种字符串文本标准。
- URI 属于父类，而 URL 属于 URI 的子类。URL 是 URI 的一个子集。
- 二者的区别在于，URI 表示请求服务器的路径，定义这么一个资源。而 URL 同时说明要如何访问这个资源（协议类型http://）

#### URL

1. URL(Uniform Resource Locator 统一资源定位器) 地址用于描述一个网络上的资源

2. **基本格式**

   protocol://hostname[:port]/pathname[;url-params][?search][#hash]

   - - - protocol：指定低层使用的协议

| **protocol** | 访问                                    | 用于                                |
| ------------ | --------------------------------------- | ----------------------------------- |
| http         | 超文本传输协议                          | 以 http:// 开头的普通网页。不加密。 |
| https        | 安全超文本传输协议(受到SSL安全凭证保护) | 安全网页，加密所有信息交换。        |
| ftp          | 文件传输协议                            | 用于将文件下载或上传至网站。        |
| file         |                                         | 您计算机上的文件。                  |

（host=hostname+port）

- hostname：域名
- port：HTTP服务器的默认端口是80（可以省略）。如果使用了别的端口，必须指明，例如：http://blog.sakuramk.cn:10086 
- pathname：访问资源的路径（包括文件）
- url-params：所带参数
- search：发送给http服务器的数据
- hash：锚

**例子**：

URL: [http://www.mywebsite.com/sj/test;id=8079?name=sviergn&x=true#stuff](http://www.myw-ebsite.com/sj/test;id=8079?name=sviergn&x=true)

- Schema: http
- host.domain: [www.mywebsite.com](http://www.mywebsite.com/)
- path: /sj/test
- URL params: id=8079
- Query String: name=sviergn&x=true
- Anchor: stuff

简单了解一下Spring-boot关于编写Controller层的一些注解

#### @Controller

@Controller：表明这个类是一个控制器类。如果用这个注解，需要在spring-mvc配置文件加上这一段，**<context:component-scan base-package="com.ztz.springmvc.controller"/>**

#### @RequestMapping

@RequestMapping：可以为控制器指定处理可以请求哪些URL请求，@RequestMapping可以定义在类或方法上。

- 类上：提供初步的请求映射信息。相对于 WEB 应用的根目录
- 方法上：提供进一步的细分映射信息。相对于类定义处的 URL。若类定义处未标注 @RequestMapping，则方法处标记的 URL 相对于WEB 应用的根目录

DispatcherServlet 截获请求后，就通过控制器上
@RequestMapping 提供的映射信息确定请求所对应的处理方法
@RequestMapping 除了可以使用请求 URL 映射请求外，还可以使用请求方法、请求参数及请求头映射请求

####  @GetMapping

用于将HTTP get请求映射到特定处理程序的方法注解，具体来说，@GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写

#### @PostMapping

用于将HTTP post请求映射到特定处理程序的方法注解，具体来说，@PostMapping是一个组合注解，是@RequestMapping(method = RequestMethod.POST)的缩写



@RequestMapping 注解可以同 @PathVaraible 注解一起使用，用来处理动态的 URI，URI 的值可以作为控制器中处理方法的参数, 同理：

- @PostMapping 注解可以同 @PathVaraible 注解一起使用
- @GetMapping 注解可以同 @PathVaraible 注解一起使用

上面两个其实等同于RequestMappin(method=RequestMethod.{}) ， 其中{}的内容可以为Get,Post,Delete...



**了解了前面这些东西之后，我们现在要做的就是控制访问以及映射访问链接到指定页面，以及在接收请求到发出相应整个过程需要处理的一些事务，从而完成我们前后端交互式的网站**



### 1. 后台登录

​	我们定义的功能如下，用户进入页面分两种情况：

- 未登录
- 已登录 (在服务器状态信息中已经有保存)

假设为第一种情况，这时候用户需要输入用户名和密码，经过如下的检查

```java
checkUser(username, password) -> queryByUsernameAndPassword(username, MD5Utils.code(password)) 
// Contrller -> Service -> Dao 这个过程涉及的层次调用
```

- 如果用户密码输入正确，则在HttpSession.session中加入用户登录状态信息，并跳转到管理员首页
- 如果登录失败，则重定向跳转到该页面，并在前端页面提示登录失败

假设为第二种情况

- 这时候服务器自动从Session中获取该用户的登录状态信息，并直接跳转到管理员首页

```java
@Controller
@RequestMapping("/admin") // 可以将多个请求映射到一个方法上去，处理多个 URI
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public String loginPage(){
        return "admin/login";
    }
	
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/admin";
        }
    }
}
```

有了登录当然也要有退出的功能，退出可以直接在Session中移除该用户的状态信息即可，后面自动跳转到/admin登录界面，实现的时候即在以上Controller加入以下代码

```java
@GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
```



### 2. 分类、标签管理

在接下来的功能实现中，我们将会体会到Thymeleaf的强大，下图是Thymeleaf的渲染过程。

![thymeleaf]($%7Bstatic%7D/thymeleaf.png)

Thymeleaf显然是一个开发页面的技术，现在各种前端技术层出不穷，比如现在主流的Vue、React、AngularJS等。很多人可能会要问，这个Thymeleaf相对于这些前端框架到底有啥优势。其实，Thymeleaf跟那些前端框架根本不是一个类型的东西，也没有啥可比性。Thymeleaf和老牌的jsp属于非前后分离的思路来开发的。后端通过数据渲染html的模板，渲染后模板就是个完整的html页面，将页面返回给请求方。

而主流的前端框架是基于前后端分离的思路来开发的，前端页面通过ajax来调用后端的rest接口来获取数据，再通过js进行渲染页面（不管什么前端技术其实都是对js进行了封装，js依然是底层核心）。

**接下来要在项目中使用Thymeleaf了，一般来说，开发一个需要渲染数据的页面，分为三个步骤：**

- 开发静态页面，即常说的模型 (我们前面讲到的页面设计)
- 获取数据 (第7节中设计的内容)
- 使用数据对静态页面进行渲染

以下面这段代码为例子，在需要根据数据来渲染的块里面，利用一些th标签来进行数据绑定，以及常用的逻辑判断

```html
 <tbody>
          <tr th:each="tag, iterStat : ${pageInfo.list}">		// 循环操作 "tag, iterStat : ${pageInfo.list}" 这个地方表示循环循环迭代的对象，后面表示迭代对象，前面为赋值数据。
            <td th:text="${iterStat.count}">1</td>				// ${}这个叫做变量表达式，用来获取数据，与它相同的有一个*{}
              													// 如果不考虑上下文的情况下，两者没有区别；星号语法评估在选定对象上表达，而不是整个上下文
            <td th:text="${tag.name}">摸鱼方法</td>
            <td>
              <a href="#" th:href="@{/admin/tags/{id}/input(id=${tag.id})}" class="ui mini teal basic button">编辑</a>
              <a href="#" th:href="@{/admin/tags/{id}/delete(id=${tag.id})}" class="ui mini red basic button">删除</a>
            </td>
          </tr>
</tbody>
```



标签页面需要一下几个功能

- 进行查询，更新, 增加，删除(通过Service->Dao->POJO 的调用来获取需要的数据，再利用Model, RedirectAttributes等对象中的属性绑定方法来绑定需要的数据对象，然后利用Thymeleaf 进行动态渲染)
- 根据数据的数量来进行分页（PageHelper分页器的使用）

以下为标签页面控制器的代码：

```java
@Controller
@RequestMapping(value = "/admin")		// 路由控制: 可以根据当前输入的
public class TypeController {

    @Autowired
    TypeService typeService;

    //@RequestParam 注解配合 @RequestMapping 一起使用，可以将请求的参数同处理方法的参数绑定在一起
    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);	// 第二个参数表示页面最大值
        List<Type> allType = typeService.getAllType();
        //得到分页结果对象
        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String toAddType(Model model){
        model.addAttribute("type", new Type());   //返回一个type对象给前端th:object
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){   //新增
        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("msg", "添加成功");
        }
        typeService.saveType(type);
        return "redirect:/admin/types";   //不能直接跳转到types页面，否则不会显示type数据(没经过types方法)
    }

    @PostMapping("/types/{id}")
    public String editType(@PathVariable Long id, Type type, RedirectAttributes attributes){  //修改
        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("msg", "修改成功");
        }
        typeService.updateType(type);
        return "redirect:/admin/types";   //不能直接跳转到types页面，否则不会显示type数据(没经过types方法)
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/types";
    }
}
```

### 3. 博客管理

```java
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
    }

    @GetMapping("/blogs")  //后台显示博客列表
    public String blogs(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Blog> allBlog = blogService.getAllBlog();
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);  //查询类型和标签
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")  //按条件查询博客
    public String searchBlogs(Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Blog> allBlog = blogService.searchAllBlog(blog);
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "查询成功");
        setTypeAndTag(model);
        return "admin/blogs";
    }

    @GetMapping("/blogs/input") //去新增博客页面
    public String toAddBlog(Model model){
        model.addAttribute("blog", new Blog());  //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/input") //去编辑博客页面
    public String toEditBlog(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
        blog.init();   //将tags集合转换为tagIds字符串
        model.addAttribute("blog", blog);     //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs") //新增、编辑博客
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes){
        //从Session获取用户信息
        //设置user属性
        blog.setUser((User) session.getAttribute("user"));
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        if (blog.getId() == null) {   //id为空，则为新增
            blogService.saveBlog(blog);
        } else {
            blogService.updateBlog(blog);
        }

        attributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/blogs";
    }
}
```

### 4. 博客首页展示

![image-20201105211436634]($%7Bstatic%7D/image-20201105211436634.png)

```java
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String toIndex(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){

        PageHelper.startPage(pagenum, 8);
        List<Blog> allBlog = blogService.getIndexBlog();
        List<Type> allType = typeService.getBlogType();  //获取博客的类型(联表查询)
        List<Tag> allTag = tagService.getBlogTag();  //获取博客的标签(联表查询)
        List<Blog> recommendBlog =blogService.getAllRecommendBlog();  //获取推荐博客

        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlogs", recommendBlog);
        return "index";
    }
}
```

### 5. 全局搜索

![image-20201105210640951]($%7Bstatic%7D/image-20201105210640951.png)

前端搜索框的实现以及一些事件的绑定, 以下为html部分代码

```html
<div class="right m-item item m-mobile-hide">
        <form name="search" action="#" method="post" th:action="@{/search}" target="_blank">
          <div class="ui icon  transparent input m-margin-tb-tiny">
            <input type="text" name="query" placeholder="Search...." th:value="${query}"> // 输入搜索词
            <i onclick="document.forms['search'].submit()" class="search link icon"></i> // 事件触发 请求方式post，根据前面输入的关键词向后端提交数据表单
          </div>
        </form>
</div>
```

后端的路由设置以及响应的Controller代码如下：

```java
@PostMapping("/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                         @RequestParam String query, Model model){

        PageHelper.startPage(pagenum, 5);
        List<Blog> searchBlog = blogService.getSearchBlog(query);
        PageInfo pageInfo = new PageInfo(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
}
```

### 6. 博客详情展示

```java
@GetMapping("/blog/{id}")
    public String toLogin(@PathVariable Long id, Model model){
        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);
        return "blog";
}
```

### 7. 博客详情评论

<img src="$%7Bstatic%7D/image-20201105212331134.png" alt="image-20201105212331134" style="zoom: 80%;" />

```html
 <div  class="ui bottom attached segment" th:if="${blog.commentabled}">
            <!--留言区域列表-->
            <div id="comment-container"  class="ui teal segment">
                <div th:fragment="commentList">
                    <div class="ui threaded comments" style="max-width: 100%;">
                        <h3 class="ui dividing header">评论</h3>
                        <div class="comment" th:each="comment : ${comments}">
                            <a class="avatar">
                                <img src="https://unsplash.it/100/100?image=1005" th:src="@{${comment.avatar}}">
                            </a>
                            <div class="content">
                                <a class="author" >
                                    <span th:text="${comment.nickname}">Matt</span>
                                    <div class="ui mini basic teal left pointing label m-padded-mini" th:if="${comment.adminComment}">博主</div>
                                </a>
                                <div class="metadata">
                                    <span class="date" th:text="${#dates.format(comment.createTime,'yyyy-MM-dd HH:mm')}">Today at 5:42PM</span>
                                </div>
                                <div class="text" th:text="${comment.content}">
                                    How artistic!
                                </div>
                                <div class="actions">
                                    <a class="reply" data-commentid="1" data-commentnickname="Matt" th:attr="data-commentid=${comment.id},data-commentnickname=${comment.nickname}" onclick="reply(this)">回复</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!--提交留言表单-->
           
            <div id="comment-form" class="ui form">
                <input type="hidden" name="blog.id" th:value="${blog.id}">
                <input type="hidden" name="parentComment.id" value="-1">
                <div class="field">
                    <textarea name="content" placeholder="请输入评论信息..."></textarea>
                </div>
                <div class="fields">
                    <div class="field m-mobile-wide m-margin-bottom-small">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <input type="text" name="nickname" placeholder="姓名" th:value="${session.user}!=null ? ${session.user.nickname}">
                        </div>
                    </div>
                    <div class="field m-mobile-wide m-margin-bottom-small">
                        <div class="ui left icon input">
                            <i class="mail icon"></i>
                            <input type="text" name="email" placeholder="邮箱" th:value="${session.user}!=null ? ${session.user.email}">
                        </div>
                    </div>
                    <div class="field  m-margin-bottom-small m-mobile-wide">
                        <button id="commentpost-btn" type="button" class="ui teal button m-mobile-wide"><i class="edit icon"></i>发布</button>
                    </div>
                </div>
            </div>
            
        </div>
    </div>
```

CommentController的实现，

```java
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")  //展示留言
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.getCommentByBlogId(blogId));
        model.addAttribute("blog", blogService.getDetailedBlog(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")   //提交留言
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getDetailedBlog(blogId));  //绑定博客与评论
        comment.setBlogId(blogId);
        User user = (User) session.getAttribute("user");
        if (user != null){   //用户为管理员
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }
        System.out.println(comment);
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
```

### 8. 归档页面

前端实现(部分重要代码)

```java
<div  class="m-container-small m-padded-tb-big" style="height: 800px!important;">
    <div class="ui container">
      <!--header-->
      <div class="ui top attached padded segment">
        <div class="ui middle aligned two column grid">
          <div class="column">
            <h3 class="ui orange header">归档</h3>
          </div>
          <div class="right aligned column">
            共 <h2 class="ui orange header m-inline-block m-text-thin" th:text="${blogCount}"> 114 </h2> 篇博客
          </div>
        </div>
      </div>

      <br><br><br>

      <th:block th:each="item : ${archiveMap}">
      <h2 class="ui center aligned header" th:text="${item.key}">2020</h2>
      <div class="ui fluid vertical menu">
        <a href="#" th:href="@{/blog/{id}(id=${blog.id})}" target="_blank" class="item" th:each="blog : ${item.value}">
          <span>
            <i class="mini orange circle icon"></i><span th:text="${blog.title}">关于刻意练习的清单</span>
            <div class="ui orange basic left pointing label m-padded-mini " th:text="${#dates.format(blog.updateTime,'MMMdd')}">9月03</div>
          </span>
          <div class="ui orange basic left pointing label m-padded-mini " th:text="${blog.flag}">原创</div>
        </a>
      </div>
      </th:block>

    </div>
  </div>
```

后端代码实现

```java
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
```

## 小功能

### 1.分页器的实现（Springboot中PageHelper 分页查询使用方法）

#### 一：导入依赖 

```java
<dependency>
	<groupId>com.github.pagehelper</groupId>
	<artifactId>pagehelper-spring-boot-starter</artifactId>
	<version>1.2.13</version>
</dependency>
```

#### 二：配置yml文件中PageHelper的属性

```java
pagehelper:                #分页插件
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
  params:
```

#### 三：在controller类中使用

- 在查询方法上调用PageHelper.startPage()方法，设置分页的页数和每页信息数
- 将查询出来的结果集用PageInfo的构造函数初始化为一个分页结果对象
- 将分页结果对象存入model，返回前端页面

```java
@GetMapping("/types")
public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){

    PageHelper.startPage(pagenum, 5);  //pagenum：页数，pagesize:每页的信息数
    
    List<Type> allType = typeService.getAllType(); //调用业务层查询方法
    
    PageInfo<Type> pageInfo = new PageInfo<>(allType); //得到分页结果对象
    
    model.addAttribute("pageInfo", pageInfo);  //携带分页结果信息
    
    return "admin/types";  //回到前端展示页面
}
```

#### 四：前端展示分页

**（thymeleaf+semantic-ui）,这里ui用自己的就行，比如bootstrap或layui，主要是thymeleaf的使用**

```java
<table  class="ui compact celled teal table">
  <thead>
  <tr>
    <th></th>
    <th>名称</th>
    <th>操作</th>
  </tr>
  </thead>
  <tbody>
  <tr th:each="type, iterStat : ${pageInfo.list}">
    <td th:text="${iterStat.count}">1</td>
    <td th:text="${type.name}">摸鱼方法</td>
    <td>
      <a href="#" th:href="@{/admin/types/{id}/input(id=${type.id})}" class="ui mini teal basic button">编辑</a>
      <a href="#" th:href="@{/admin/types/{id}/delete(id=${type.id})}" class="ui mini red basic button">删除</a>
    </td>
  </tr>
  </tbody>
  <tfoot>
  <tr>
    <th colspan="7">
      <div class="ui mini pagination menu"  >
        <div class="item"><a th:href="@{/admin/types}">首页</a></div>
        <div class="item"><a th:href="@{/admin/types(pagenum=${pageInfo.hasPreviousPage}?${pageInfo.prePage}:1)}">上一页</a></div>
        <div class="item"><a th:href="@{/admin/types(pagenum=${pageInfo.hasNextPage}?${pageInfo.nextPage}:${pageInfo.pages})}">下一页</a></div>
        <div class="item"><a th:href="@{/admin/types(pagenum=${pageInfo.pages})}">尾页</a></div>
      </div>
      <a href="#" th:href="@{/admin/types/input}" class="ui mini right floated teal basic button">新增</a>
    </th>
  </tr>
  </tfoot>
</table>

<div class="ui segment m-inline-block">
  <p >当前第<span th:text="${pageInfo.pageNum}"></span>页，总<span th:text="${pageInfo.pages}"></span>页，共<span th:text="${pageInfo.total}"></span>条记录</p>
</div>
```

#### 五：效果展示（pagesize设置为5的效果）

![image-20201105213853797]($%7Bstatic%7D/image-20201105213853797.png)



## 部署到服务器

- 方式一：可以用本机，开放一下端口，做一下内网穿透（相关步骤自行度娘，谷歌...）
- 方式二（推荐）：租一台阿里云服务器，然后配置好环境， 接着在机器上开放服务端口，并在阿里云控制台防火墙规则处添加一下对应端口，然后打包你的项目成一个jar包（利用maven打包）, 最后上传到服务器，运行-jar包，别人就可以访问你的网站了。

![image-20201105223400381]($%7Bstatic%7D/image-20201105223400381.png)

![image-20201105223420635]($%7Bstatic%7D/image-20201105223420635.png)

