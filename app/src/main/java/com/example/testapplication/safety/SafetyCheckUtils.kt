package com.example.testapplication.safety

import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.GsonUtils
import java.nio.charset.Charset
import java.util.*
import kotlin.Exception
import kotlin.math.abs

/**
 * 安全检查的工具。主要是对规则和相关动作进行操作
 * 规则如下:
 *  1、检查内容是加密后的还是非加密后的，内容以"-"开头表示已加密密码数据,否则是未加密的密码数据
 */
object SafetyCheckUtils {
    /** 加密的密码构造器 */
    val DES_ALGRITHM = "DES/CBC/PKCS5Padding"
    /** 初始密码按构造因子 */
    val DES_INIT_IV = "00112233".toByteArray()

    /**
     * 将密码构建后重新保存回原位置的时候所使用的的加密的密码
     */
    private val SAVE_PASSWORD_PASS = "996633..".toByteArray()
    /**
     * 输入在指定位置的密码是未加密的还是已加密的数据区分标志,如果是已加密的则是以此分隔符开头
     */
    private val INPUT_PASS_START_SPEC_CHAT = "-"
    /**
     * 各种数据的组装之间的分隔符，就是需要分割的数据
     */
    private val DATA_SPEC_CHAT = INPUT_PASS_START_SPEC_CHAT

    /**
     * 计算最终密码。通过相关的元数据计算密码。也即是最终用户需要输入的密码,这个是在基础校验之火最后一步验证
     * 这个密码也就是此设备最开始需要输入的原始密码
     * 计算方法:
     * 1、读取原始数据
     * 2、加密‘1’中组织奖的数据得到密文
     * 3、将‘2’中的密文进行hashCode计算然后进行绝对值运算
     * 4、将‘3’中的绝对值数值进行拆分。然后将每位数字相加得到密码
     *
     * @param srcData 原始数据信息
     * @return 如果为空表示计算失败
     */
    fun calculateFinalPassword(srcData: SafetyReadOrWriteBean): String? {
        try {
            //计算第1步：组装元数据
            val enSourceDataStr = srcData.generateCode
            //计算第2步：加密组装的数据
            val enStr = EncryptUtils.encryptDES2HexString(
                enSourceDataStr.toByteArray(),
                SAVE_PASSWORD_PASS,
                DES_ALGRITHM,
                DES_INIT_IV
            )
            //计算第3步：将密文进行hashCode运算取得单纯的数字 字符
            val hasCode = abs(enStr.hashCode()).toString()
            //计算第4步:计算最终的密码
            var finalPass = 1
            for (i in hasCode.indices) {
                val valu = hasCode[i].toInt()
                if(i.rem(2) == 0){
                    finalPass *= valu
                }else{
                    finalPass /= 7 //主要是为了让密码出现较大的波动
                }
            }
            return finalPass.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    /**
     * 生成程序的code。生成唯一的id，格式如下:
     *  【androidId】-【5位数字码】
     *
     * @return 返回生成的id
     */
    fun generateAppId(): String {
        //5位数字码
        val numCode = Random().nextInt(89999) + 10000
        return "${DeviceUtils.getAndroidID()}$DATA_SPEC_CHAT${numCode}"
    }

    /**
     * 解析新生成的密码。
     * @param savePass 新保存的密码(即:[buildNewSavePass]方法生成之后保存的密码数据)
     * @return SafetyReadOrWriteBean
     */
    fun parsingNewSavePass(savePass: String): SafetyReadOrWriteBean {
        try {
            val passStr = savePass.replaceFirst(INPUT_PASS_START_SPEC_CHAT, "")
            val deStr = EncryptUtils.decryptHexStringDES(
                passStr,
                SAVE_PASSWORD_PASS,
                DES_ALGRITHM,
                DES_INIT_IV
            )
            return GsonUtils.fromJson(
                String(deStr, Charset.forName("UTF-8")),
                SafetyReadOrWriteBean::class.java
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return SafetyReadOrWriteBean(
                generateCode = "",
                inputPass = ""
            )
        }
    }

    /**
     * 将输入的密码重构然后重新保存至原位置。以此防止串改以及被拷贝复用等问题
     * 注意:
     * 1、并不是直接将原密码直接加密保存。而是还会加入一些其他信息
     * 2、此密码将会和系统相关id绑定。防止被重用
     * 3、生成码是第一次使用的时候生成的。后续将会继续沿用，除非删除配置文件、刷机、格式化sd卡、恢复出厂设等操作将会造成重新生成code
     * @param generatedCode 程序生成的code码(格式为:【androidId】-【5位数字码】)
     * @param inputPass 输入的原始密码
     * @return 构建好之后的数据。此数据将直接重新保存到输入密码的原位置进行替换
     *         如果为空，表示验证出错了
     */
    fun buildNewSavePass(generatedCode: String, inputPass: String): String? {
        try {
            val params = SafetyReadOrWriteBean(
                generateCode = generatedCode,
                inputPass = inputPass
            )
            val enStr = EncryptUtils.encryptDES2HexString(
                GsonUtils.toJson(params).toByteArray(Charset.forName("UTF-8")),
                SAVE_PASSWORD_PASS,
                DES_ALGRITHM,
                DES_INIT_IV
            )
            return "${INPUT_PASS_START_SPEC_CHAT}${enStr}"
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * 检查输入的
     * @param pass String
     * @return T:表示已经加密过的数据，F:还是原始输入数据。还未加密过
     */
    fun checkInputPassIsEncryption(pass: String): Boolean {
        return pass.startsWith(INPUT_PASS_START_SPEC_CHAT)
    }
}