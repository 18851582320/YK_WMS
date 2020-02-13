package com.iwfm.entity.weiXin;

/**
 * ClassName: CommonButton 
 * @Description: 普通按钮（子按钮）
 * @author yk
 * @date 2018年5月10日
 */
public class CommonButton extends Button {
        private String type;
        private String key;

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public String getKey() {
                return key;
        }

        public void setKey(String key) {
                this.key = key;
        }
}