/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pokemon.test.logic;

import co.edu.uniandes.csw.pokemon.constants.PokemonType;
import co.edu.uniandes.csw.pokemon.ejb.PokemonLogic;
import co.edu.uniandes.csw.pokemon.entities.PokemonEntity;
import co.edu.uniandes.csw.pokemon.entities.PokemonEntity.Ataque;
import co.edu.uniandes.csw.pokemon.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.pokemon.persistence.PokemonPersistence;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author juanj
 */
@RunWith(Arquillian.class)
public class PokemonLogicTest {
    
    @Inject
    private PokemonLogic pokemonLogic;
    
    @Inject
    UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PokemonEntity.class.getPackage())
                .addPackage(PokemonPersistence.class.getPackage())
                .addPackage(PokemonLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest() 
    {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() 
    {
        em.createQuery("delete from PokemonEntity").executeUpdate();
    }
    
    
    @Test(expected = BusinessLogicException.class)
    public void createPokemonTestPuteado() throws BusinessLogicException 
    {
        PodamFactory factory = new PodamFactoryImpl();
        PokemonEntity newEntity = factory.manufacturePojo(PokemonEntity.class);
        PokemonEntity result = pokemonLogic.createPokemon(newEntity);
        Assert.assertNotNull(result);

        PokemonEntity entity = em.find(PokemonEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test
    public void createPokemonTest() throws BusinessLogicException 
    {
        PodamFactory factory = new PodamFactoryImpl();
        PokemonEntity newEntity = factory.manufacturePojo(PokemonEntity.class);
        
        newEntity.setTipo(PokemonType.FIRE);
        newEntity.setDebilidad(PokemonType.WATER);
        
        List <Ataque> ataques = newEntity.getAtaques();
        for (int i = 0; i< ataques.size(); i++) 
        {
            if (ataques.get(i).dano<10|| ataques.get(i).dano>100) 
            {
                Ataque newAtaque = ataques.get(i);
                newAtaque.dano = 69;
                ataques.set(i, newAtaque);
            }
        }
        
        newEntity.setAtaques(ataques);
        
        PokemonEntity result = pokemonLogic.createPokemon(newEntity);
        Assert.assertNotNull(result);

        PokemonEntity entity = em.find(PokemonEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());

    } 
}
