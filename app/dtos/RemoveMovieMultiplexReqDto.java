package dtos;

public class RemoveMovieMultiplexReqDto {

    public Integer multiplexid;

    public RemoveMovieMultiplexReqDto() {
    }

    public RemoveMovieMultiplexReqDto(Integer multiplexid) {
        this.multiplexid = multiplexid;
    }

    public Integer getMultiplexid() {
        return multiplexid;
    }

    public void setMultiplexid(Integer multiplexid) {
        this.multiplexid = multiplexid;
    }

    @Override
    public String toString() {
        return "RemoveMovieMultiplexReqDto{" +
                "multiplexid=" + multiplexid +
                '}';
    }
}
