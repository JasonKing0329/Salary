package com.king.app.salary.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 13:31
 */
@Entity(nameInDb = "salary_detail")
public class SalaryDetail {

    @Id(autoincrement = true)
    private Long id;

    // 基础
    private float basic;

    // 技能
    private float tech;

    // 绩效
    private float performance;

    // 绩效补发/工资/补发
    private float supply;

    // 值班费/加班费/其它
    private float overtime;

    // 电话补贴
    private float allowancePhone;

    // 餐补
    private float allowanceFood;

    // 防暑降温/采暖补贴
    private float allowanceSeason;

    // 其他补贴
    private float extraRaise;

    // 其他补贴说明
    private String extraRaiseDesc;

    // 事假\病假扣款\旷工
    private float absence;

    // 个所税
    private float tax;

    // 应纳税总额
    private float totalToTax;

    // 养老保险
    private float insurancePension;

    // 失业保险
    private float insuranceJobless;

    // 医疗保险
    private float insuranceHealth;

    // 大额医疗
    private float insuranceHugeMedical;

    // 公积金
    private float housingFund;

    // 其他扣款
    private float extraDrop;

    // 其他扣款说明
    private String extraDropDesc;

    // 实发总额（不算补贴）
    private float receiveAllowanceLess;

    @Generated(hash = 2029226638)
    public SalaryDetail(Long id, float basic, float tech, float performance,
            float supply, float overtime, float allowancePhone, float allowanceFood,
            float allowanceSeason, float extraRaise, String extraRaiseDesc,
            float absence, float tax, float totalToTax, float insurancePension,
            float insuranceJobless, float insuranceHealth,
            float insuranceHugeMedical, float housingFund, float extraDrop,
            String extraDropDesc, float receiveAllowanceLess) {
        this.id = id;
        this.basic = basic;
        this.tech = tech;
        this.performance = performance;
        this.supply = supply;
        this.overtime = overtime;
        this.allowancePhone = allowancePhone;
        this.allowanceFood = allowanceFood;
        this.allowanceSeason = allowanceSeason;
        this.extraRaise = extraRaise;
        this.extraRaiseDesc = extraRaiseDesc;
        this.absence = absence;
        this.tax = tax;
        this.totalToTax = totalToTax;
        this.insurancePension = insurancePension;
        this.insuranceJobless = insuranceJobless;
        this.insuranceHealth = insuranceHealth;
        this.insuranceHugeMedical = insuranceHugeMedical;
        this.housingFund = housingFund;
        this.extraDrop = extraDrop;
        this.extraDropDesc = extraDropDesc;
        this.receiveAllowanceLess = receiveAllowanceLess;
    }

    @Generated(hash = 1746533635)
    public SalaryDetail() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getBasic() {
        return this.basic;
    }

    public void setBasic(float basic) {
        this.basic = basic;
    }

    public float getTech() {
        return this.tech;
    }

    public void setTech(float tech) {
        this.tech = tech;
    }

    public float getPerformance() {
        return this.performance;
    }

    public void setPerformance(float performance) {
        this.performance = performance;
    }

    public float getSupply() {
        return this.supply;
    }

    public void setSupply(float supply) {
        this.supply = supply;
    }

    public float getOvertime() {
        return this.overtime;
    }

    public void setOvertime(float overtime) {
        this.overtime = overtime;
    }

    public float getAllowancePhone() {
        return this.allowancePhone;
    }

    public void setAllowancePhone(float allowancePhone) {
        this.allowancePhone = allowancePhone;
    }

    public float getAllowanceFood() {
        return this.allowanceFood;
    }

    public void setAllowanceFood(float allowanceFood) {
        this.allowanceFood = allowanceFood;
    }

    public float getAllowanceSeason() {
        return this.allowanceSeason;
    }

    public void setAllowanceSeason(float allowanceSeason) {
        this.allowanceSeason = allowanceSeason;
    }

    public float getAbsence() {
        return this.absence;
    }

    public void setAbsence(float absence) {
        this.absence = absence;
    }

    public float getTax() {
        return this.tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getTotalToTax() {
        return this.totalToTax;
    }

    public void setTotalToTax(float totalToTax) {
        this.totalToTax = totalToTax;
    }

    public float getInsurancePension() {
        return this.insurancePension;
    }

    public void setInsurancePension(float insurancePension) {
        this.insurancePension = insurancePension;
    }

    public float getInsuranceJobless() {
        return this.insuranceJobless;
    }

    public void setInsuranceJobless(float insuranceJobless) {
        this.insuranceJobless = insuranceJobless;
    }

    public float getInsuranceHealth() {
        return this.insuranceHealth;
    }

    public void setInsuranceHealth(float insuranceHealth) {
        this.insuranceHealth = insuranceHealth;
    }

    public float getInsuranceHugeMedical() {
        return this.insuranceHugeMedical;
    }

    public void setInsuranceHugeMedical(float insuranceHugeMedical) {
        this.insuranceHugeMedical = insuranceHugeMedical;
    }

    public float getHousingFund() {
        return this.housingFund;
    }

    public void setHousingFund(float housingFund) {
        this.housingFund = housingFund;
    }

    public float getReceiveAllowanceLess() {
        return this.receiveAllowanceLess;
    }

    public void setReceiveAllowanceLess(float receiveAllowanceLess) {
        this.receiveAllowanceLess = receiveAllowanceLess;
    }

    public float getExtraDrop() {
        return this.extraDrop;
    }

    public void setExtraDrop(float extraDrop) {
        this.extraDrop = extraDrop;
    }

    public String getExtraDropDesc() {
        return this.extraDropDesc;
    }

    public void setExtraDropDesc(String extraDropDesc) {
        this.extraDropDesc = extraDropDesc;
    }

    public float getExtraRaise() {
        return this.extraRaise;
    }

    public void setExtraRaise(float extraRaise) {
        this.extraRaise = extraRaise;
    }

    public String getExtraRaiseDesc() {
        return this.extraRaiseDesc;
    }

    public void setExtraRaiseDesc(String extraRaiseDesc) {
        this.extraRaiseDesc = extraRaiseDesc;
    }

}
