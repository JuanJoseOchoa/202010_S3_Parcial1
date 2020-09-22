/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pokemon.dtos;

import co.edu.uniandes.csw.pokemon.constants.PokemonType;
import co.edu.uniandes.csw.pokemon.entities.PokemonEntity;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author juanj
 */
public class PokemonDTO implements Serializable{

    
    private String nombre;
    
    private String descripcion;
    
    private Double peso;
    
    private Double altura;
   
    private PokemonType tipo;
    
    private PokemonType debilidad; 
    
    private List <PokemonEntity.Ataque> atks;
     
    private Long id;
    
    
    /**
     * Constructor por defecto
     */
    public PokemonDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param productoEntity: Es la entidad que se va a convertir a DTO
     */
    public PokemonDTO(PokemonEntity pokemonEntity) 
    {
        if (pokemonEntity != null) 
        {
            this.id = pokemonEntity.getId();
            this.nombre = pokemonEntity.getNombre();
            this.descripcion = pokemonEntity.getDescripcion();
            this.peso = pokemonEntity.getPeso();
            this.altura = pokemonEntity.getAltura();
            this.tipo = pokemonEntity.getTipo();
            this.debilidad = pokemonEntity.getDebilidad();
            this.atks = pokemonEntity.getAtaques();
        }
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public PokemonEntity toEntity() 
    {
        PokemonEntity pokemonEntity = new PokemonEntity();
        pokemonEntity.setId(this.getId());
        pokemonEntity.setNombre(this.getNombre());
        pokemonEntity.setPeso(this.getPeso());
        pokemonEntity.setAltura(this.getAltura());
        pokemonEntity.setTipo(this.getTipo());
        pokemonEntity.setDebilidad(this.getDebilidad());
        return pokemonEntity;
    }
    
    /**
     * Obtiene el atributo id.
     *
     * @return atributo id.
     *
     */
    public Long getId() 
    {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     *
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the peso
     */
    public Double getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    /**
     * @return the altura
     */
    public Double getAltura() {
        return altura;
    }

    /**
     * @param altura the altura to set
     */
    public void setAltura(Double altura) {
        this.altura = altura;
    }

    /**
     * @return the tipo
     */
    public PokemonType getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(PokemonType tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the debilidad
     */
    public PokemonType getDebilidad() {
        return debilidad;
    }

    /**
     * @param debilidad the debilidad to set
     */
    public void setDebilidad(PokemonType debilidad) {
        this.debilidad = debilidad;
    }

    /**
     * @return the atks
     */
    public List <PokemonEntity.Ataque> getAtks() {
        return atks;
    }

    /**
     * @param atks the atks to set
     */
    public void setAtks(List <PokemonEntity.Ataque> atks) {
        this.atks = atks;
    }
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
