/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pokemon.test.persistence;

import co.edu.uniandes.csw.pokemon.entities.PokemonEntity;
import co.edu.uniandes.csw.pokemon.persistence.PokemonPersistence;
import java.util.ArrayList;
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
public class PokemonPersistenceTest {
    
    @Inject
    private PokemonPersistence pokemonP;
    
    @PersistenceContext(unitName = "pokemonPU")
    private EntityManager po;
    
    @Inject
    UserTransaction utx;
    
    @Deployment
    public static JavaArchive createDeployment( )
    {
        return ShrinkWrap.create(JavaArchive.class)
        .addPackage(PokemonEntity.class.getPackage())
	.addPackage(PokemonPersistence.class.getPackage())
        .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
        .addAsManifestResource("META-INF/beans.xml", "beans.xml" );
    }

    @Before
    public void configTest() {	
        try {	
            utx.begin();	
            po.joinTransaction();	
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
    
        private void clearData() 
        {	
            po.createQuery("delete from PokemonEntity").executeUpdate();	
        }	
        
    @Test
    public void createTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        PokemonEntity pokemon = factory.manufacturePojo(PokemonEntity.class);
        PokemonEntity result  = pokemonP.create(pokemon);
        Assert.assertNotNull(result);
        
        PokemonEntity entity = po.find(PokemonEntity.class, result.getId());

        Assert.assertEquals(result.getNombre(), entity.getNombre());
        Assert.assertEquals(result.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(result.getDebilidad(), entity.getDebilidad());
    }
    
}
