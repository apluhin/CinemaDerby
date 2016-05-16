package convertor;

/**
 * Created by aleksejpluhin on 25.04.16.
 */
public interface Convertor<Input,Output> {
    Output convert(Input input);
}
