package br.inf.consult.progle.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.gson.Gson;

import lombok.NonNull;
import lombok.Value;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IntegracaoUtil {

	private static final OkHttpClient client = new OkHttpClient();
	private static final Gson gson = new Gson();

	public Optional<Cep> searchByCep(@NonNull String cep) {
		return cepFromRequest(
				new Request.Builder().url(String.format("https://www.cepaberto.com/api/v3/cep?cep=%s", cep.replaceAll("-", "")))
						.addHeader("Authorization", "Token token=6961d59906c84df14aa5e81409fbc16c").build());
	}

	private Optional<Cep> searchByAddress(@NonNull String estado, @NonNull String cidade, String logradouro) {
		return cepFromRequest(new Request.Builder()
				.url(String.format("https://www.cepaberto.com/api/v3/address?estado=%s&cidade=%s&logradouro=%s", estado,
						cidade, logradouro))
				.addHeader("Authorization", "Token token=6961d59906c84df14aa5e81409fbc16c").build());
	}

	private Optional<Cep> cepFromRequest(Request request) {
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return Optional.of(gson.fromJson(response.body().string(), Cep.class));
			}
			return Optional.empty();
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	private Optional<RetornoDistanciaDTO> calcularDistanciaGoogleMaps(@NonNull String cepOrigem,
			@NonNull String cepDestino) {
		String url = String.format(
				"https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + cepOrigem + "&destinations=+"
						+ cepDestino
						+ "&mode=driving&language=pt-BR&sensor=false&key=AIzaSyC22rCP8JRusQvFcyAKqeaAJv-m7FyDhlM",
				cepOrigem, cepDestino);

		Request request = new Request.Builder().url(url).build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful() && response.body() != null) {
				return Optional.of(gson.fromJson(response.body().string(), RetornoDistanciaDTO.class));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

	private String[] obterChavePixQrCode(@NonNull String email, @NonNull String nome, @NonNull String cpf,
			@NonNull String referenceId, int valor) throws IOException {
		{

			OkHttpClient client = new OkHttpClient();

//		  String formattedValue = String.format("%,.2f", valor)

			MediaType mediaType = MediaType.parse("application/json");
			String jsonRequest = "{\"customer\":{\"email\":\"" + email + "\",\"tax_id\":\"" + cpf + "\",\"name\":\""
					+ nome + "\"},\"reference_id\":\"" + referenceId + "\",\"qr_codes\":[{\"amount\":{\"value\":\""
					+ valor + "\"}}]}";
			RequestBody body = RequestBody.create(mediaType, jsonRequest);
			Request request = new Request.Builder().url("https://sandbox.api.pagseguro.com/orders").post(body)
					.addHeader("accept", "application/json")
					.addHeader("Authorization",
							"Bearer b6508835-f773-40be-b572-4a0bea63f1f4318f12ae438093a19d3b4c198cb593ca0480-502a-45f1-ba83-cb55feeeeaa4")
					.addHeader("x-client-id", "<your-client-id>").addHeader("x-client-secret", "<your-client-secret>")
					.addHeader("content-type", "application/json").build();

			try (Response response = client.newCall(request).execute()) {
				if (response.isSuccessful() && response.body() != null) {
					Payment payment = gson.fromJson(response.body().string(), Payment.class);
					String[] retorno = new String[2];
					retorno[0] = payment.getQr_codes().get(0).getText();
					retorno[1] = payment.getQr_codes().get(0).getLinks().get(0).getHref();
					return retorno;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	private Optional<Payment> obterStatusPagamento(@NonNull String orderId) throws IOException {
		{

			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder().url("https://sandbox.api.pagseguro.com/orders/" + orderId).get()
					.addHeader("accept", "application/json")
					.addHeader("Authorization",
							"Bearer b6508835-f773-40be-b572-4a0bea63f1f4318f12ae438093a19d3b4c198cb593ca0480-502a-45f1-ba83-cb55feeeeaa4")
					.addHeader("x-client-id", "<your-client-id>").addHeader("x-client-secret", "<your-client-secret>")
					.build();

			try (Response response = client.newCall(request).execute()) {
				if (response.isSuccessful() && response.body() != null) {
					return Optional.of(gson.fromJson(response.body().string(), Payment.class));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return Optional.empty();
		}
	}

	public int obterKmDistanciaEntreCeps(String cepOrigem, String cepDestino) {
		RetornoDistanciaDTO distancia = this.calcularDistanciaGoogleMaps(cepOrigem, cepDestino).orElse(null);
		int retorno = distancia.getRows().get(0).getElements().get(0).getDistance().getValue() / 1000;
		return retorno;

	}

	public static void main(String[] args) throws IOException {
		IntegracaoUtil api = new IntegracaoUtil();

		System.out.println(api.searchByCep("09260310"));

		System.out.println(api.obterKmDistanciaEntreCeps("58055510", "09260310"));

		String referenceId = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
		String[] chavePix = api.obterChavePixQrCode("fmeder@gmail.com", "Eder", "22691433889", referenceId, 100);
		;
		System.out.println(chavePix[0] + " - " + chavePix[1]);
	}

	@Value
	public class Cep {
		private String cep;
		private double altitude;
		private double latitude;
		private double longitude;
		private String bairro;
		private String logradouro;
		private Cidade cidade;
		private Estado estado;

		@Value
		public class Cidade {
			private String nome;
			private Integer ddd;
			private String ibge;
		}

		@Value
		public class Estado {
			private String sigla;
		}
	}

	@Value
	private class DistanciaDTO {
		private String cepOrigem;
		private String cepDestino;

	}

	@Value
	private class Amount {
		private String value;
	}

	@Value
	private class Customer {
		private String email;
		private String tax_id;
		private String name;
	}

	@Value
	private class QrCode {
		private String id;
		private String expiration_date;
		private String text;
		private Amount amount;
		private ArrayList<Link> links;
	}

	@Value
	private class Link {
		private String href;
	}

	@Value
	private class Payment {
		private String id;
		private Customer customer;
		private String reference_id;
		private ArrayList<QrCode> qr_codes;
	}

	@Value
	private class Distance {
		private String text;
		private int value;
	}

	@Value
	private class Duration {
		private String text;
		private String value;
	}

	@Value
	private class Element {
		private Distance distance;
		private Duration duration;
		private String status;
	}

	@Value
	private class Row {
		ArrayList<Element> elements;
	}

	@Value
	private class RetornoDistanciaDTO {
		ArrayList<String> destination_addresses;
		ArrayList<String> origin_addresses;
		ArrayList<Row> rows;
		String status;
	}

}
