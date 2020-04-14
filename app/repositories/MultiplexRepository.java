package repositories;

import dtos.MultiplexDto;
import entities.Multiplex;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

@Singleton
public class MultiplexRepository {

    @Inject
    private JPAApi jpaApi;

    private <T> T wrap(Function<EntityManager, T> function){
        return this.jpaApi.withTransaction(function);
    }


    @Transactional
    public Multiplex insert(Multiplex multiplex){
        jpaApi.withTransaction(entityManager -> {
            entityManager.persist(multiplex);
        });
        return multiplex;
    }

    public List<Multiplex> list(){
        return this.wrap(entityManager -> {
            List<Multiplex> multiplexes =  entityManager.createQuery("select m from Multiplex m", Multiplex.class).getResultList();
            return multiplexes;
        });
    }

    public Multiplex findById(Integer id){
        return this.wrap(entityManager -> {
            Multiplex multiplex = entityManager.find(Multiplex.class,id);
            return multiplex;
        });
    }

    public boolean delete(Integer multiplexId){
        jpaApi.withTransaction(entityManager -> {
            Multiplex multiplex = entityManager.find(Multiplex.class,multiplexId);
            entityManager.remove(multiplex);
            return true;
        });
        return false;
    }

    public Multiplex insert(MultiplexDto multiplexDto){
        return this.wrap(entityManager -> {
            Multiplex multiplex =  entityManager.find(Multiplex.class,multiplexDto.getId());
            multiplex.setMovie(multiplexDto.getMovie());
            entityManager.merge(multiplex);
            return multiplex;
        });
    }

    public Multiplex findByMultiplexIdAndScreenName(String multiplexName, String screenName){
        return this.wrap(entityManager -> {
            String queryString = "select m from Multiplex m where m.multiplexname = '"+multiplexName+"' and m.screename = '"+screenName+"'";
            Multiplex multiplex =  entityManager.createQuery(queryString, Multiplex.class).getSingleResult();
            return multiplex;
        });
    }

}
