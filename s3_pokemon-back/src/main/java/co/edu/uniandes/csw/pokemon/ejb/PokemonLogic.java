/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pokemon.ejb;

import co.edu.uniandes.csw.pokemon.entities.PokemonEntity;
import co.edu.uniandes.csw.pokemon.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.pokemon.persistence.PokemonPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author juanj
 */
@Stateless
public class PokemonLogic {
    
    private static final Logger LOGGER = Logger.getLogger(PokemonLogic.class.getName());

    @Inject
    private PokemonPersistence persistence;

    /**
     * Se encarga de crear un producto en la base de datos.
     *
     * @param productoEntity Objeto de ProductoEntity con los datos nuevos
     * @return Objeto de ProductoEntity con los datos nuevos y su ID.
     * @throws
     * co.edu.uniandes.csw.visitadoresmedicos.exceptions.BusinessLogicException
     */
    public PokemonEntity createPokemon(PokemonEntity pokemonEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del Pokemon");

        if (persistence.findByName(pokemonEntity.getNombre())!= null) 
        {
            throw new BusinessLogicException("No pueden existir dos Pokemones con el mismo nombre.");
        }
        
        if (pokemonEntity.getDebilidad().equals(pokemonEntity.getTipo())) 
        {
            throw new BusinessLogicException("No pueden existir un Pokemon que tiene el mismo tipo y la misma debilidad.");
        }
        
        if (!pokemonEntity.getAtaques().isEmpty()) 
        {
            throw new BusinessLogicException("El malparido Pokemon debe tener por lo menos un ataque, como piensa pelear petardo sin ataques?.");
        }
        
        for (PokemonEntity.Ataque ataque : pokemonEntity.getAtaques()) 
        {
            if (ataque.dano < 10 || ataque.dano > 100) 
            {
                throw new BusinessLogicException("El malparido Pokemon no es Arceus? Esta ud hackeando?.");
            }
        
        }
        
        PokemonEntity newProductoEntity = persistence.create(pokemonEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del Pokemon");
        return newProductoEntity;
    }
}
