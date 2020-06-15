package dto;

/**
 *
 * @author rando
 */
public class CompleteDTO {

    private final ChuckDTO chuckQoute;
    private final KanyeRestDTO kanyeRestQoute;

    public CompleteDTO(ChuckDTO chuckQoute, KanyeRestDTO kanyeRestQoute) {
        this.kanyeRestQoute = kanyeRestQoute;
        this.chuckQoute = chuckQoute;
    }

    public ChuckDTO getChuckQoute() {
        return chuckQoute;
    }

    public KanyeRestDTO getKanyeRestQoute() {
        return kanyeRestQoute;
    }

    @Override
    public String toString() {
        return "CompleteDTO{" + "chuckQoute=" + chuckQoute + ", kanyeRestQoute=" + kanyeRestQoute + '}';
    }


    

}
