//package cn.okcoming.dbproxy.client.demo;
//
//
//import cn.okcoming.baseutils.Md5;
//import cn.okcoming.baseutils.ObjectMapperUtils;
//import cn.okcoming.dbproxy.bean.QueryRequest;
//import cn.okcoming.dbproxy.util.Constants;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.UUID;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = MainApplication.class)
//@AutoConfigureMockMvc
//public class MockMvcTests {
//
//	@Autowired
//	private MockMvc mvc;
//
//	@Test
//	public void query() throws Exception {
//		QueryRequest request = new QueryRequest();
//		request.setDatabase("database1");
//		request.setProject("project1");
//		request.setParameters(new Object[]{5});
//		request.setQuery("select id,cell,lost_contact_date,gmt_created from black_overdue_idcard where lost_contact_date is not null limit ?");
//
//		String uri = "/query";
//
//		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(ObjectMapperUtils.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)
//				.header(Constants.X_UUID, UUID.randomUUID().toString())
//				.accept(MediaType.APPLICATION_JSON))
//				.andReturn();// 得到返回结果
//
//		MockHttpServletResponse response = mvcResult.getResponse();
//		//拿到请求返回码
//		int status = response.getStatus();
//		//拿到结果
//		String contentAsString = response.getContentAsString();
//
//		log.info("query {}",status);
//		log.info("query {}",contentAsString);
//	}
//}
