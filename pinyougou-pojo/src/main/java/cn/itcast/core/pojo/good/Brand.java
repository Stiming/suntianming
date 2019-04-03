package cn.itcast.core.pojo.good;

import java.io.Serializable;
import java.util.Objects;

public class Brand implements Serializable {
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌首字母
     */
    private String firstChar;

    /**
     * 品牌状态   0(未审核)  1(审核中)  2(审核通过)  3(关闭)
     */
    private Long status;

    private static final long serialVersionUID = 1L;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar == null ? null : firstChar.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id) &&
                Objects.equals(name, brand.name) &&
                Objects.equals(firstChar, brand.firstChar) &&
                Objects.equals(status, brand.status);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstChar='" + firstChar + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, firstChar, status);
    }
}