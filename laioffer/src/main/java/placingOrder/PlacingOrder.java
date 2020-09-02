package placingOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.MySQLDBConnection;
import entity.Order;
import entity.Robot;
import robotManagement.Point;
import rpcHelper.RpcHelper;

/**
 * Servlet implementation class PlacingOrder
 */
public class PlacingOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlacingOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		
		JSONObject input = new JSONObject(IOUtils.toString(request.getReader()));
		JSONObject object = new JSONObject();
		
		try {
			String username = (String)session.getAttribute("username");
			String start = input.getString("start");
			String destination = input.getString("destination");
			String method = input.getString("method");
			String dispatcher = input.getString("dispatcher");
			JSONArray route = input.getJSONArray("route");
			List<Point> points = new ArrayList<>();
			
			for(int i = 0; i < route.length(); i++) {
				JSONObject obj = route.getJSONObject(i);
				Point point = new Point(obj.getDouble("lat"), obj.getDouble("lng"));
				points.add(point);
			}
			
			CreateOrder creation = new CreateOrder();
			Order order = creation.createOrder(username, start, destination, method, dispatcher, points);
			object = order.toJSONObject();
			
			MySQLDBConnection connection = new MySQLDBConnection();
			Robot robot = connection.getRobot(order.getRobotId());
			object.put("method", robot.getType());
			object.put("dispatcher", robot.getDispatcher());
			connection.close();
			
		} catch (JSONException e) {
			object.put("status", "Can not place order, information missing");
		} catch (NullPointerException e) {
			object.put("status", "No Free Robot");
		}
		
		RpcHelper.writeJsonObject(response, object);
	}
}
