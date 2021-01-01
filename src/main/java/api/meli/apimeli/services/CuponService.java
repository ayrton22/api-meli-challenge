package api.meli.apimeli.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CuponService {

    public List<String> calculate(Map<String, Float> items, Float amount) {

        List<String> resultado = new ArrayList<String>();
        List<String> listaIds = new ArrayList<String>();
        List<Float> precios = new ArrayList<Float>();

        // recorrer el mapa inicial para crear los arrays de la clave(listasIds) y
        // valor(precios)
        Iterator<String> it = items.keySet().iterator();
        while (it.hasNext()) {
            String itemId = it.next();
            Float valor = items.get(itemId);

            listaIds.add(itemId);
            precios.add(valor);
        }
        // ordenar de menor a mayor la lista de precios
        // y la lista de codigos segun su precio
        burbuja(precios, listaIds);

        System.out.println(precios);
        System.out.println(listaIds);

        float total = 0;
        int itemsAgregados = 0;
        int index = 0;
        while (total < amount && precios.size() > index) {

            if (total + precios.get(itemsAgregados) < amount) {

                resultado.add(listaIds.get(itemsAgregados));
                total = total + precios.get(itemsAgregados);
                itemsAgregados++;
            }
            index++;
        }

        return resultado;
    }

    public void burbuja(List<Float> precios, List<String> codigos) {
        int i, j;
        Float aux;
        String auxCodigo;
        for (i = 0; i < precios.size() - 1; i++) {
            for (j = 0; j < precios.size() - i - 1; j++) {
                if (precios.get(j + 1) < precios.get(j)) {

                    aux = precios.get(j + 1);
                    precios.set(j + 1, precios.get(j));
                    precios.set(j, aux);

                    auxCodigo = codigos.get(j + 1);
                    codigos.set(j + 1, codigos.get(j));
                    codigos.set(j, auxCodigo);
                }
            }
        }
    }

}
