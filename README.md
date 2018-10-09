# LookBar2
安卓组件化项目demo
> 基于豆瓣免费api开发的练手APP
### 主要模块有：
1. commonlib(公共module，封装其它组件公共的方法和基类，其它组件皆依赖此module)
2. commonres(公共资源module，commonlib依赖此module)
3. 电影模块组件
4. 书籍模块组件
5. 音乐模块组件
6. main模块组件
7. app壳工程(空壳工程，只做application初始化设置)

### 设置
在gradle.properties文件中，设置isModule=true时，可单独编译3、 4、 5、 6四个独立模块；  
设置isModule=false时,可编译运行app模块。

### 应用截图
#### 1. app运行截图：  
![image](https://github.com/Roben1016/LookBar2/raw/master/screenshot/module_app.png) 

#### 2. main组件独立运行截图：  
![image](https://github.com/Roben1016/LookBar2/raw/master/screenshot/module_main.png)

#### 3. 电影组件独立运行截图：  
![image](https://github.com/Roben1016/LookBar2/raw/master/screenshot/module_movie.png)  

#### 4. 书籍组件独立运行截图：  
![image](https://github.com/Roben1016/LookBar2/raw/master/screenshot/module_book.png)  

#### 5. 音乐组件独立运行截图：  
![image](https://github.com/Roben1016/LookBar2/raw/master/screenshot/module_music.png)  
