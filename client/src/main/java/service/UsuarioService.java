package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Usuario;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class UsuarioService {
    private static final String BASE_URL = "http://localhost:8080/server/api/usuarios";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public UsuarioService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    @SuppressWarnings("deprecation")
    public String crearUsuario(Usuario usuario) throws Exception {
        String json = mapper.writeValueAsString(usuario);
    
        HttpPost post = new HttpPost(BASE_URL);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(json));
    
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {
    
            if (response.getCode() == 201) {
                return "Usuario registrado con éxito";
            } else if (response.getCode() == 409) {
                return "Id ya agregada";
            } else {
                throw new Exception("Error al registrar usuario: " + response.getCode());
            }
        }
    }

    public List<Usuario> getUsuarios() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), new TypeReference<List<Usuario>>() {});
        } else {
            throw new Exception("Error al obtener estudiantes: " + response.body());
        }
    }
}
