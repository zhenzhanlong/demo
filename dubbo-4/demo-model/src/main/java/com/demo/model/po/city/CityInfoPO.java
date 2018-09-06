package com.demo.model.po.city;

public class CityInfoPO  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 991905244015970010L;
	/** 主键ID */
    private Long id;
    
    /** 类型ID */
    private Long type_id;
    
    /** 城市编码 */
    private Long city_code;
    
    /** 城市名称 */
    private String city_name;
    
    /** 城市编码值 */
    private Long code_value;
    
    /** 上级编码 */
    private Long up_code_id;
    
    /** 序列号 */
    private Long sequence_no;
    
    /** 银行备注 */
    private String remark;
    
    /** 渠道类型**/
    private String channel_type;
    
    public CityInfoPO() {
		super();
	}

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	/**
     * 获取主键ID
     * 
     * @return 主键ID
     */
     public Long getId() {
        return this.id;
     }
     
    /**
     * 设置主键ID
     * 
     * @param id
     *          主键ID
     */
     public void setId(Long id) {
        this.id = id;
     }
    
    /**
     * 获取类型ID
     * 
     * @return 类型ID
     */
     public Long getType_id() {
        return this.type_id;
     }
     
    /**
     * 设置类型ID
     * 
     * @param type_id
     *          类型ID
     */
     public void setType_id(Long type_id) {
        this.type_id = type_id;
     }
    
    /**
     * 获取城市编码
     * 
     * @return 城市编码
     */
     public Long getCity_code() {
        return this.city_code;
     }
     
    /**
     * 设置城市编码
     * 
     * @param city_code
     *          城市编码
     */
     public void setCity_code(Long city_code) {
        this.city_code = city_code;
     }
    
    /**
     * 获取城市名称
     * 
     * @return 城市名称
     */
     public String getCity_name() {
        return this.city_name;
     }
     
    /**
     * 设置城市名称
     * 
     * @param city_name
     *          城市名称
     */
     public void setCity_name(String city_name) {
        this.city_name = city_name;
     }
    
    /**
     * 获取城市编码值
     * 
     * @return 城市编码值
     */
     public Long getCode_value() {
        return this.code_value;
     }
     
    /**
     * 设置城市编码值
     * 
     * @param code_value
     *          城市编码值
     */
     public void setCode_value(Long code_value) {
        this.code_value = code_value;
     }
    
    /**
     * 获取上级编码
     * 
     * @return 上级编码
     */
     public Long getUp_code_id() {
        return this.up_code_id;
     }
     
    /**
     * 设置上级编码
     * 
     * @param up_code_id
     *          上级编码
     */
     public void setUp_code_id(Long up_code_id) {
        this.up_code_id = up_code_id;
     }
    
    /**
     * 获取序列号
     * 
     * @return 序列号
     */
     public Long getSequence_no() {
        return this.sequence_no;
     }
     
    /**
     * 设置序列号
     * 
     * @param sequence_no
     *          序列号
     */
     public void setSequence_no(Long sequence_no) {
        this.sequence_no = sequence_no;
     }
    
    /**
     * 获取银行备注
     * 
     * @return 银行备注
     */
     public String getRemark() {
        return this.remark;
     }
     
    /**
     * 设置银行备注
     * 
     * @param remark
     *          银行备注
     */
     public void setRemark(String remark) {
        this.remark = remark;
     }
     
	
     
}
