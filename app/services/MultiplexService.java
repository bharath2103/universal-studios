package services;

import dtos.MultiplexDto;
import entities.Multiplex;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import repositories.MovieRepository;
import repositories.MultiplexRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MultiplexService {

    @Inject
    private MultiplexRepository multiplexRepository;

    @Inject
    private MovieRepository movieRepository;


    public Multiplex convertToMultiplex(MultiplexDto multiplexDto){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(MultiplexDto.class, Multiplex.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(multiplexDto, Multiplex.class);
    }

    public MultiplexDto convertToMultiplexDto(Multiplex multiplex){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Multiplex.class, MultiplexDto.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(multiplex, MultiplexDto.class);
    }

    public MultiplexDto findById(Integer id) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Multiplex.class, MultiplexDto.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Multiplex multiplex = multiplexRepository.findById(id);
        MultiplexDto multiplexDto = this.convertToMultiplexDto(multiplex);
        return multiplexDto;
    }

    public MultiplexDto addMultiplex(MultiplexDto multiplexDto){
        Multiplex multiplex = new Multiplex(multiplexDto.multiplexname
                , multiplexDto.address
                , multiplexDto.numberofscreens
                , multiplexDto.screename);
        Multiplex addedMultiplex = this.multiplexRepository.insert(multiplex);
        MultiplexDto addedMultiplexDto = new MultiplexDto(addedMultiplex.id
                , addedMultiplex.multiplexname
                , addedMultiplex.address
                , addedMultiplex.numberofscreens
                , addedMultiplex.screename
                , addedMultiplex.movie);
        return addedMultiplexDto;
    }

    public List<MultiplexDto> getAllMultiplexes(){
        List<MultiplexDto> multiplexes = new ArrayList<>();
        List<Multiplex> multiplexList = this.multiplexRepository.list();
        multiplexes = multiplexList.stream()
                .map(multiplex-> new MultiplexDto(multiplex.getId()
                                                    , multiplex.getMultiplexname()
                                                    , multiplex.getAddress()
                                                    , multiplex.getNumberofscreens()
                                                    , multiplex.getScreename()
                                                    , multiplex.getMovie()))
                .collect(Collectors.toList());
        return multiplexes;
    }

    public boolean removeMultiplex(Integer multiplexId){
        return this.multiplexRepository.delete(multiplexId);
    }

    public MultiplexDto updateMultiplex(MultiplexDto multiplexDto){
        Multiplex multiplex = multiplexRepository.insert(multiplexDto);
        MultiplexDto multiplexDto1 = new MultiplexDto(multiplex.getId()
                                                    , multiplex.getMultiplexname()
                                                    , multiplex.getAddress()
                                                    , multiplex.getNumberofscreens()
                                                    , multiplex.getScreename()
                                                    , multiplex.getMovie());
        return multiplexDto1;
    }

    public MultiplexDto findByMultiplexIdAndScreenName(String multiplexName, String screenName){
        Multiplex multiplex = multiplexRepository.findByMultiplexIdAndScreenName(multiplexName, screenName);
        return this.convertToMultiplexDto(multiplex);
    }
}
