package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.EnderecoTinyDTO;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Victor Ramos
 */

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Adicionando mapeamento manual de atributo da entidade para DTO (Para casos onde nome dos atributos não dão match)
        // No caso abaixo, não precisaria dessa intervenção manual, pois os atributos da entidade e do DTO dão match nas assinaturas
//        modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
//                .addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setTaxaFrete)

        TypeMap<Endereco, EnderecoTinyDTO> enderecoToEnderecoTinyDTO =
                modelMapper.createTypeMap(Endereco.class, EnderecoTinyDTO.class);

        enderecoToEnderecoTinyDTO.<String>addMapping(
                src -> src.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().setEstado(value)
        );

        return modelMapper;
    }
}
