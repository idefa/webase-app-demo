package cn.lijunhua.patient.entity;

import cn.lijunhua.patient.bcospoco.Column;
import cn.lijunhua.patient.bcospoco.PrimaryKey;
import cn.lijunhua.patient.bcospoco.TableName;
import lombok.Data;

@TableName(name = "tb_patient_info",title = "患者表合约")
@PrimaryKey(name = "phone")
@Data
public class PatientInfo {

    @Column(name = "phone",title = "手机号")
    private String phone;

    @Column(name = "patient_name",title = "患者名称")
    private String patientName;

    @Column(name = "email",title = "邮箱")
    private String email;

    @Column(name = "group",title = "组")
    private int group;
}
