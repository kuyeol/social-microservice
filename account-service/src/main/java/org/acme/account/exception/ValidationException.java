

package org.acme.account.exception;

import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;


/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
public final class ValidationException extends RuntimeException implements Consumer<ValidationError> {

	private final Map<String, List<Error>> errors = new HashMap<>();

	public ValidationException() {}

	public ValidationException(ValidationError error) {
		addError(error);
	}

	public List<Error> getErrors() {
		return errors.values().stream().reduce(new ArrayList<>(), (l, r) -> {
			l.addAll(r);
			return l;
		}, (l, r) -> l);
	}

	public boolean hasError(String... types) {
		if (types.length == 0) {
			return !errors.isEmpty();
		}

		for (String type : types) {
			if (errors.containsKey(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there are validation errors related to the attribute with the given {@code name}.
	 *
	 * @param name
	 * @return
	 */
	public boolean isAttributeOnError(String... name) {
		if (name.length == 0) {
			return !errors.isEmpty();
		}

		List<String> names = Arrays.asList(name);

		return errors.values().stream().flatMap(Collection::stream).anyMatch(error -> names.contains(error.getAttribute()));
	}

	@Override
	public void accept(ValidationError error) {
		addError(error);
	}

	void addError(ValidationError error) {
		List<Error> errors = this.errors.computeIfAbsent(error.getMessage(), (k) -> new ArrayList<>());
		errors.add(new Error(error));
	}

	@Override
	public String toString() {
		return "ValidationException [errors=" + errors + "]";
	}

	@Override
	public String getMessage() {
		return toString();
	}

	public Response.Status getStatusCode() {
		for (Map.Entry<String, List<Error>> entry : errors.entrySet()) {
			for (Error error : entry.getValue()) {
				if (!Response.Status.BAD_REQUEST.equals(error.getStatusCode())) {
					return error.getStatusCode();
				}
			}
		}
		return Response.Status.BAD_REQUEST;
	}

	public static class Error implements Serializable {

		private final ValidationError error;

		public Error(ValidationError error) {
			this.error = error;
		}

		public String getAttribute() {
			return error.getMessage();
		}

		public String getMessage() {
			return error.getMessage();
		}
		
		public Object[] getMessageParameters() {
			return error.getLocalizedMessageParams();
		}

		@Override
		public String toString() {
			return "Error [error=" + error + "]";
		}

		public String getFormattedMessage(BiFunction<String, Object[], String>  messageFormatter) {
			return messageFormatter.apply(getMessage(), getMessageParameters());
		}

		public Response.Status getStatusCode() {
			return Response.Status.fromStatusCode( error.hashCode() );
		}
	}

}
