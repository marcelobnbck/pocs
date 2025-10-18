package org.example.guitarmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "guitar_models")
public class GuitarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String type;
    private int strings;

    public GuitarModel() {}

    public GuitarModel(String brand, String model, String type, int strings) {
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.strings = strings;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getStrings() { return strings; }
    public void setStrings(int strings) { this.strings = strings; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuitarModel that = (GuitarModel) o;
        return strings == that.strings && Objects.equals(id, that.id) && Objects.equals(brand, that.brand) && Objects.equals(model, that.model) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, type, strings);
    }

    @Override
    public String toString() {
        return "GuitarModel{" + "id=" + id + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", type='" + type + '\'' + ", strings=" + strings + '}';
    }
}
