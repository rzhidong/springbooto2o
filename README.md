1. 去掉spring、mybatis，迁移pom
2. 配置dao,迁移dao、entity、mapper
3. 配置service,迁移dto、enums、exception、service、cache
4. 配置web,迁移web
5. 前端迁移，修改静态文件addResourceHandlers配置
6. 验证码迁移(由于web.xml不生效了，需要在MvcConfig配置Kaptcha验证码[ServletServletRegistrationBean])
7. 替代docBase配置显示图片(addResourceHandlers)
8. 配置拦截器，迁移interceptor