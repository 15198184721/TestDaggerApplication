package com.example.testapplication.safety

/**
 * 读取或者保存所对应的实体类
 */
class SafetyReadOrWriteBean(
    /**
     * 程序运行生成的唯一码
     * 格式：
     *  (androidId)-(随机一个数字码])
     */
    val generateCode:String,
    /**
     * 外部输入的密码
     */
    val inputPass:String
)