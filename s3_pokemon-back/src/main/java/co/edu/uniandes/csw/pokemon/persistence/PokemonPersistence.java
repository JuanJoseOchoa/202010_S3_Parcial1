/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pokemon.persistence;

import co.edu.uniandes.csw.pokemon.entities.PokemonEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author juanj
 */
@Stateless
public class PokemonPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(PokemonPersistence.class.getName());
    
    @PersistenceContext(unitName = "pokemonPU")
    protected EntityManager po;
    
    public PokemonEntity create(PokemonEntity pokemon)
    {   
        LOGGER.log(Level.INFO, "Creando un Pokemon nuevo");
        po.persist(pokemon);
        LOGGER.log(Level.INFO, "Pokemon creado");
        return pokemon;
    }
    
    public List<PokemonEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todos los Pokemon");
        TypedQuery query = po.createQuery("select u from PokemonEntity u", PokemonEntity.class);
        return query.getResultList();
    }
        
    public PokemonEntity find(Long id) 
    {	
        LOGGER.log(Level.INFO, "Consultando el Pokemon con id={0}", id);	
        return po.find(PokemonEntity.class, id);	
    }
    
    public PokemonEntity findByName(String nombrePokemon) 
    {
        LOGGER.log(Level.INFO, "Consultando los pokemones por nombre ", nombrePokemon);
        TypedQuery query = po.createQuery("Select e From PokemonEntity e where e.nombre = :nombrePokemon", PokemonEntity.class);
        query = query.setParameter("nombre", nombrePokemon);
        List<PokemonEntity> sameName = query.getResultList();
        PokemonEntity result;

        if (sameName == null) 
        {
            result = null;
        } 
        
        else if (sameName.isEmpty()) 
        {
            result = null;
        } 
        
        else 
        {
            result = sameName.get(0);
        }
        
        LOGGER.log(Level.INFO, "Saliendo de consultar pokemones por nombre ", nombrePokemon);
        return result;
    }
    
        
    public PokemonEntity update(PokemonEntity pokemon) 
    {
        LOGGER.log(Level.INFO, "Actualizando el Pokemon con id={0}", pokemon.getId());	
        return po.merge(pokemon);
    }
        
    public void delete(Long id) 
    {
        LOGGER.log(Level.INFO, "Borrando el Pokemon con id={0}", id);	
        PokemonEntity pokemonEntity = po.find(PokemonEntity.class, id);
        po.remove(pokemonEntity);
    } 
}
