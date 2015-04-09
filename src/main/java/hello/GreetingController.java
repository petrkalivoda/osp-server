package hello;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/osp/myserver")
public class GreetingController {
	private final AtomicLong counter = new AtomicLong();
	private final Map<String, Boolean> strings = new ConcurrentHashMap<String, Boolean>();

	@RequestMapping(method = RequestMethod.POST, value = "/data", consumes = MediaType.TEXT_PLAIN_VALUE)
	public void acceptData(@RequestBody String body) throws Exception {
		processText(body);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/count", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getCounter() throws Exception {
		return String.valueOf(resetCounter());
	}

	private void processText(String text) {
		StringTokenizer tok = new StringTokenizer(text);
		while (tok.hasMoreTokens()) {
			strings.put(tok.nextToken(), Boolean.TRUE);
		}

		counter.set(strings.size());
	}

	private long resetCounter() {
		long value = counter.get();
		counter.set(0);
		strings.clear();
		return value;
	}

}