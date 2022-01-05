package character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.LinkedList;

import main.Audio;
import main.GamePanel;

public class AI {
	
	GamePanel gamePanel;
	
	//final ArrayList<String> adjacentTile = new ArrayList<String>( Arrays.asList("N", "S", "W", "E") );
	//enum DIR { N, S, W, E, O; }; // North, South, East, West, O : point zero
	//enum DIR { N, S, W, E; }; // North, South, East, West
	
	// ----- Domi
	class Station {
		int x, y; // Coordinates of a station point in path
	}
	public class Route {
		ArrayList<DIR> moves = new ArrayList<DIR>(); // The various directions of the path
		ArrayList<Station> stations = new ArrayList<Station>(); // The various station points of the path
	}
	/*
	class Role {
		static final int DoNothing = 0;
		static final int CHASE = 1;
		static final int HIDE = 2;
	}
	*/
	enum ROLE { CHASE, HIDE, RANDOM; };
	
	DIR prevDir = DIR.O;
	
	public AI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public Route navigate(Route route) {
		return route;	
	}
	
	public Route evaluateRouteMaxMin(int c1x, int c1y, DIR pDir, int c2x, int c2y, ROLE role) {
		
		Route selectedRoute = null;
		ArrayList<Route> c1Routes = getRoutes(c1x, c1y, pDir, 1);
		ArrayList<Route> c2Routes = getRoutes(c2x, c2y, DIR.O, 6);
		ArrayList<Integer> distances = new ArrayList<Integer>();
		ArrayList<Integer> distancesSum = new ArrayList<Integer>();
		for(Route c1Route : c1Routes) {
			
			distances = new ArrayList<Integer>();
			
			for(Route c2Route : c2Routes) {
				int dx = c1Route.stations.get(c1Route.stations.size()-1).x - 
						c2Route.stations.get(c2Route.stations.size()-1).x;
				dx = dx>=0 ? dx : - dx;
				int dy = c1Route.stations.get(c1Route.stations.size()-1).y - 
						c2Route.stations.get(c2Route.stations.size()-1).y;
				dy = dy>=0 ? dy : - dy;
				distances.add(dx+dy);
			}
			
			switch (role) {
			case CHASE:
				distancesSum.add(Collections.max(distances));
				break;
			case HIDE:
				distancesSum.add(Collections.min(distances)); // distances: [] -> java.util.NoSuchElementException
				break;
			case RANDOM :
				
				break;
			default :
				
				break;	
			}
			
			
		}
		
		int index;
		switch (role) {
		case CHASE:
			index = distancesSum.indexOf( Collections.min(distancesSum) ); // Returns the index of first occurance of min value
			break;
		case HIDE:
			index = distancesSum.indexOf( Collections.max(distancesSum) );
			break;
		case RANDOM :
			index = (int)(Math.random()*c1Routes.size());
			break;
		default :
			index = (int)(Math.random()*c1Routes.size());
			break;	
		}
		/*
		if(role==ROLE.CHASE) {
			index = distances.indexOf( Collections.min(distancesSum) );
		}
		else if(role==ROLE.HIDE) {
			index = distances.indexOf( Collections.max(distancesSum) );
		}
		else index = (int)(Math.random()*c1Routes.size());
		*/
		
		selectedRoute = c1Routes.get(index);
		
		selectedRoute.moves.remove(0);
		selectedRoute.stations.remove(0);
		for(Station st : selectedRoute.stations) {
			st.x -= gamePanel.tileSize/2;
			st.y -= gamePanel.tileSize/2;
		}
		
		/*
		System.out.println("pdir: "+pDir);
		System.out.println("selectedRoute: ");
		System.out.println("moves: "+selectedRoute.moves);
		System.out.print("    ");
		for(DIR dr : selectedRoute.moves) {
			System.out.print(dr+"     ->     ");
		}
		System.out.println("\nstations:");
		for(Station st : selectedRoute.stations) {
			System.out.print("("+st.x+","+st.y+") -> ");
		}
		System.out.print("\n");
		
		if(selectedRoute.moves.size()==0) {
			for(int d : distancesSum) {
				System.out.println(d);
			}
		}
		System.out.println(distances);
		System.out.println(index);
		*/
		
		return selectedRoute;
	}
	
	
	public Route evaluateNextMove(int c1x, int c1y, int c2x, int c2y, ROLE role) {
		
		Route selectedRoute; // Prosoxi na min gyrisei null giati tha exw starvation(tha tin ksanakalei kai pali null tha gyrizei)
		
		// Last Chance to escape! If the distance between you and the opponent is
		// less than socialDistance, forget where you came from, back-n-forth allowed
		int socialDistance = 2*gamePanel.tileSize;
		if(Math.abs(c1x-c2x)<socialDistance && Math.abs(c1y-c2y)<socialDistance) {
			prevDir = DIR.O; // Use this here if you don't want to remember your previous path last move direction
			//gamePanel.getAudioChannel().play(Audio.SWOOSH);
			if(Math.abs(c1x-c2x)<gamePanel.tileSize/2) new Audio().play(Audio.SWOOSH);
		}
		
		// Me c1scope=1 exw provlima stin eisodo pou pagwnei (bug1)
		selectedRoute = evaluateRoute(c1x, c1y, prevDir, c2x, c2y, role, 4, 1); // Ean anevoun poly ta VisualRange mallon kathisterei opote exw exception stin Minotaur giati vriskei to route=null
		//Route selectedRoute = ai.evaluateRouteMaxMin(x, y, pDir, e.x, e.y, ROLE.HIDE);
		
		//* Sublist of ArrayList
		//* fromIndex – low endpoint (inclusive) of the subList
	    //* toIndex – high endpoint (exclusive) of the subList
		//* https://www.geeksforgeeks.org/arraylist-sublist-method-in-java-with-examples/
		// Select only a sublist of with the first moves of selectedRoute to execute
		int firstMovesSelection = 1;
		//System.out.println("selectedRoute.moves.size(): "+selectedRoute.moves.size());
		ArrayList<DIR> movesSublist = new ArrayList<DIR>(selectedRoute.moves.subList(0, firstMovesSelection)); // Prosoxi an i selectedRoute.size()<firstMoves
		ArrayList<Station> stationsSublist = new ArrayList<Station>(selectedRoute.stations.subList(0, firstMovesSelection));
		selectedRoute.moves = movesSublist;
		selectedRoute.stations = stationsSublist;
		
		
		prevDir = selectedRoute.moves.get(selectedRoute.moves.size()-1); //prevDir = DIR.O; // Use this here if you want to remember your previous path last move direction (avoid back-n-forth while transitioning routes)
		
		//System.out.println("selectedRoute.moves: "+selectedRoute.moves);
		
		return selectedRoute;
	}
	
	private Route evaluateRoute(int c1x, int c1y, DIR prevDir, int c2x, int c2y, ROLE role, int c1VisualRange, int c2VisualRange) {
		
		Route selectedRoute = null;
		
		// Last Chance to escape , back-n-forth allowed
		//if(Math.abs(c1x-c2x)<32 && Math.abs(c1y-c2y)<32) pDir = DIR.O;
		
		// Ean apexeis to poly socialDistance tiles apo ton antipalo, tote prospathise na sygklineis
		// sto kentro wste na apofigeis ton egklovismo se gwnies
		/*
		int socialDistance = 4;
		if(Math.abs(c1x-c2x)>=socialDistance*gamePanel.tileSize || 
				Math.abs(c1y-c2y)>=socialDistance*gamePanel.tileSize) {
			c2x=gamePanel.screenWidth/2;
			c2y=gamePanel.screenHeight/2;
			role = ROLE.CHASE;
		}
		*/
		
		// Ean apexeis ligotero apo 3 tiles apo ton antipalo, tote pare to route pou ta stations tou
		// exoun tin megalyteri apostasi apo ta pithana stations twn route tou antipalou (krata apostaseis)
		ArrayList<Route> c1Routes = getRoutes(c1x, c1y, prevDir, c1VisualRange);
		ArrayList<Route> c2Routes = getRoutes(c2x, c2y, DIR.O, c2VisualRange);
		ArrayList<Integer> stationsDistances = new ArrayList<Integer>(); // Apostaseis kathe simeiou gia ena route tou character1 apo kathe simeio gia ena route tou character2
		ArrayList<Integer> routeDistancesAvg = new ArrayList<Integer>(); // Mesos oros apostasewn kathe simeiou gia ena route tou character1 apo kathe simeio gia ena route tou character2
		ArrayList<Integer> routeDistancesAvgSum = new ArrayList<Integer>(); // Athroisma meswn orwn apostasewn apo kathe route tou character1 apo kathe route tou character2 -> Me skopo na epileksw route megistopoiontas/elaxistopoiontas ayto to athrisma
		for(Route c1Route : c1Routes) {
			// stationsDistances: Oi meses apostaseis metaksy tou c1Route kai olwn twn c2Routes
			routeDistancesAvg = new ArrayList<Integer>();
			for(Route c2Route : c2Routes) {
				// stationsDistances: Oi apostaseis twn station metaksy tou c1Route kai c2Route
				stationsDistances = new ArrayList<Integer>();
				for(Station c1Station : c1Route.stations) {
					for(Station c2Station : c2Route.stations) {
						int dx = c1Station.x-c2Station.x;
						dx = dx>=0 ? dx : -dx;
						int dy = c1Station.y-c2Station.y;
						dy = dy>=0 ? dy : -dy;
						//distances.add(dx+dy);
						// An kapoio simeio tou route peftei panw se dynamei route tou antipaloy prospathise na to apofygeis
						stationsDistances.add(dx<32 && dy<32 ? -999999 : dx+dy);
					}
				}
				int distanceAvg = 0;
				for(int d : stationsDistances) {
					distanceAvg += d; 
				}
				routeDistancesAvg.add(distanceAvg/stationsDistances.size());
			}
			int distanceAvgSum = 0;
			for(int d : routeDistancesAvg) {
				distanceAvgSum += d; 
			}
			routeDistancesAvgSum.add(distanceAvgSum);
			
		}
		
		//System.out.println("routeDistancesAvgSum: "+routeDistancesAvgSum);
		
		int index;
		switch (role) {
		case CHASE:
			index = routeDistancesAvgSum.indexOf( Collections.min(routeDistancesAvgSum) ); // Returns the index of first occurance of min value
			//System.out.println("c1Routes.size(): "+c1Routes.size());
			//System.out.println("distancesSum: "+ distancesSum+" , index: "+ index);
			break;
		case HIDE:
			//System.out.println("c1Routes.size(): "+c1Routes.size());
			//System.out.println("distancesSum: "+ distancesSum);
			index = routeDistancesAvgSum.indexOf( Collections.max(routeDistancesAvgSum) );
			break;
		case RANDOM :
			index = (int)(Math.random()*c1Routes.size());
			break;
		default :
			index = (int)(Math.random()*c1Routes.size());
			break;	
		}
		/*
		if(role==ROLE.CHASE) {
			index = distances.indexOf( Collections.min(distancesSum) );
		}
		else if(role==ROLE.HIDE) {
			index = distances.indexOf( Collections.max(distancesSum) );
		}
		else index = (int)(Math.random()*c1Routes.size());
		*/
		
		selectedRoute = c1Routes.get(index);
		
		// Remove home station so you have only next moves directions and stations, ready to use for navigation
		selectedRoute.moves.remove(0);
		selectedRoute.stations.remove(0);
		for(Station st : selectedRoute.stations) {
			st.x -= gamePanel.tileSize/2;
			st.y -= gamePanel.tileSize/2;
		}
		
		
		return selectedRoute;
	}
	
	public ArrayList<Route> getRoutes(int x, int y, DIR prevDir, int maxNumOfRouteStations) {
		
		ArrayList<Route> validPaths = new ArrayList<Route>();
		//ArrayList<DIR> collisions = new ArrayList<DIR>();
		//ArrayList<DIR> notRedundantMoves = new ArrayList<DIR>();
		
		// ---- Arxikopoiisi
		Route newRoute = new Route();
		
		newRoute.moves.add(prevDir);
		Station station = new Station();
		
		int speed = gamePanel.tileSize;
		//int speed = gamePanel.tileSize/2;
		
		/*
		solidArea.x = gamePanel.tileSize/4;
		solidArea.y = gamePanel.tileSize/4;
		solidArea.width = gamePanel.tileSize/2;
		//solidArea.width = Math.round(1 - 2*solidArea.x);
		solidArea.height = gamePanel.tileSize/2;
		*/
		// ta kentra tou tile kai tou collision rectange prepei na simpiptoun
		station.x = x + gamePanel.tileSize/2;
		//station.x = x/gamePanel.tileSize; // Se poio arithmitika tile vriskomai orizontivws
		//station.x = station.x*gamePanel.tileSize + gamePanel.tileSize/2; // vale me sto kentro tou tile
		station.y = y + gamePanel.tileSize/2;
		//station.y = y/gamePanel.tileSize;
		//station.y = station.y*gamePanel.tileSize + gamePanel.tileSize/2;
		newRoute.stations.add(station);
		
		validPaths.add(newRoute);
		
		int newRoutesAddedStart = 0;
		int newRoutesAddedEnd = 0;
		int recursions = 1;
		
		CollisionDetection collisionDetection = new CollisionDetection(gamePanel);
		
		List<DIR> listOfDIR = new LinkedList<DIR>(Arrays.asList(DIR.values()));
		listOfDIR = listOfDIR.subList(0,listOfDIR.size()-1); // Take a subList from enum (if you have add to it O at the end) so you get only N, S, W, E
		//List<DIR> listOfDIR = Arrays.asList(DIR.values()); // https://stackoverflow.com/questions/2965747/why-do-i-get-an-unsupportedoperationexception-when-trying-to-remove-an-element-f
		//ArrayList<DIR> notRedundantMoves = new ArrayList<DIR>(EnumSet.allOf(DIR.class));
		
		do {	
			//continuePaths = plausiblePaths.subList(plausiblePaths.size()-1-newMovesAdded, plausiblePaths.size());
			
			for(int r=newRoutesAddedStart; r<=newRoutesAddedEnd; r++) {
				
				Route recentlyAddedRoute = validPaths.get(r);
				
				// Avoid back and forth
				DIR lastMove = recentlyAddedRoute.moves.get(recentlyAddedRoute.moves.size()-1);
				List<DIR> notRedundantMoves = new ArrayList<>(listOfDIR);
				//List<DIR> notRedundantMoves = listOfDIR; // Again if you don't new everytime you remove from notRedundantMoves will remove them from listOfDIR too!!!
				
				//System.out.println(notRedundantMoves + " , " + lastMove);
				if (lastMove.equals(DIR.N)) notRedundantMoves.remove(DIR.S);
				else if (lastMove.equals(DIR.S)) notRedundantMoves.remove(DIR.N);
				else if (lastMove.equals(DIR.W)) notRedundantMoves.remove(DIR.E);
				else if (lastMove.equals(DIR.E)) notRedundantMoves.remove(DIR.W);
				//System.out.println("notRedundantMoves: "+notRedundantMoves);
					
				int currentX = recentlyAddedRoute.stations.get(recentlyAddedRoute.stations.size()-1).x;
				int currentY = recentlyAddedRoute.stations.get(recentlyAddedRoute.stations.size()-1).y;
				
				ArrayList<DIR> collisions = collisionDetection.getCollisions(currentX - gamePanel.tileSize/2, currentY - gamePanel.tileSize/2, speed);
				
				
				// Gia na min exw exception se dead ends me ta avoid back and forth kai mou girizei null routes
				int d=0;
				for(DIR collision : collisions) {
					d++;
					if (!notRedundantMoves.contains(collision)) break;
				}
				if(d==notRedundantMoves.size()) notRedundantMoves = new ArrayList<>(listOfDIR);
				
				
				for(DIR newMove2Check : notRedundantMoves) {
					int nextX = currentX;
					int nextY = currentY;
					//System.out.println("newMove2Check: "+newMove2Check);
					
					if (newMove2Check.equals(DIR.N) && !collisions.contains(DIR.N) && currentY-speed>0 ) 
						nextY = currentY-speed;
					else if (newMove2Check.equals(DIR.S) && !collisions.contains(DIR.S) && currentY+speed<gamePanel.screenHeight) 
						nextY = currentY+speed;
					else if (newMove2Check.equals(DIR.W) && !collisions.contains(DIR.W) && currentX-speed>0) 
						nextX = currentX-speed;
					else if (newMove2Check.equals(DIR.E) && !collisions.contains(DIR.E) && currentX+speed<gamePanel.screenWidth) 
						nextX = currentX+speed;
					else {
						//route.d_x = route.s_x; route.d_y = route.s_y;
						continue;
					}
					//System.out.println("New Station added (nextX, nextY): "+"("+nextX+","+nextY+") | New move added: "+newMov2Check);
					
					newRoute = new Route();
					//newRoute.moves = recentlyAddedRoute.moves;
					//* Copy list, otherwise if use the above is like pointer: every modification will affect every occuranse of the list moves inside any route object
					//* https://www.baeldung.com/java-copy-list-to-another
					newRoute.moves = new ArrayList<>(recentlyAddedRoute.moves); 
					
					// WRONG(see above) newRoute.stations = recentlyAddedRoute.stations;
					newRoute.stations = new ArrayList<>(recentlyAddedRoute.stations);
					newRoute.moves.add(newMove2Check);
					station = new Station();
					station.x = nextX;
					station.y = nextY;
					newRoute.stations.add(station);
					
					
					// DO NOT Remove the starting point from the route here. Will do in evaluate route. You may need initial position in caluculations at some evaluate path AI method.
					//newRoute.moves.remove(0);
					//newRoute.stations.remove(0);
					
					
					validPaths.add(newRoute);
					
				}
				
				//newMovesAddedCollector += newMovesAdded;
				
				// https://stackoverflow.com/questions/16644811/converting-a-sublist-of-an-arraylist-to-an-arraylist
				//continuePaths = plausiblePaths.subList(plausiblePaths.size()-1-newMovesAdded, plausiblePaths.size());
				// https://www.javacodeexamples.com/java-arraylist-sublist-example/985
				/*
		         * Important: Start index is inclusive but end index is exclusive.
		         * So in our example, element at 0 index will be included but element
		         * at 5th index will not be included in the sublist.
		         */
				//class java.util.ArrayList$SubList cannot be cast to class java.util.ArrayList (java.util.ArrayList$SubList and java.util.ArrayList are in module java.base of loader 'bootstrap')
				
			}
			
			newRoutesAddedStart = newRoutesAddedEnd + 1;
			newRoutesAddedEnd = validPaths.size()-1;
			recursions++;
			
		} while(recursions<=maxNumOfRouteStations);
		
		
		//* Sublist of ArrayList
		//* fromIndex – low endpoint (inclusive) of the subList
	    //* toIndex – high endpoint (exclusive) of the subList
		//* https://www.geeksforgeeks.org/arraylist-sublist-method-in-java-with-examples/
		
		validPaths = new ArrayList<Route>(validPaths.subList(newRoutesAddedStart, newRoutesAddedEnd+1));
		// Take a sublist of Routes with the latest added stations (Routes with same size=maxNumOfRouteStations)
		// If you want all sizes validPaths for some reason cause Exception in evaluateRoute
		
		return validPaths;
	}
	
	protected ArrayList<DIR> tileCollisionDetection(int x, int y) {
		// Returns an ArrayList with the sides of tile that collide(the adjacent tiles are solid)
		
		// The variables are int, thus any remainder from division is suppressed
		
		int tileCol = x/gamePanel.tileSize;
		int tileRow = y/gamePanel.tileSize;
		
		int tileCenterX = tileCol*gamePanel.tileSize + gamePanel.tileSize/2;
		int tileCenterY = tileRow*gamePanel.tileSize + gamePanel.tileSize/2;
		
		//System.out.println("(tileCol, tileRow) -> "+tileCol+" , "+tileRow);
			
		int neighborColOrRow, tileID;
		
		ArrayList<DIR> collision = new ArrayList<DIR>();
		
		for (DIR adjacentTile : DIR.values()) {
			
			if (adjacentTile.equals(DIR.N)) { // Check Northern Tile
				neighborColOrRow = (tileCenterY - gamePanel.tileSize)/gamePanel.tileSize;
				tileID = gamePanel.tileManager.labyrinth[tileCol][neighborColOrRow];
				if(gamePanel.tileManager.tile[tileID].isSolid==true) collision.add(DIR.N);
				//System.out.println(tileCol+" , "+tileRow);
			}
			if (adjacentTile.equals(DIR.S)) { // Check Southern Tile
				neighborColOrRow = (tileCenterY + gamePanel.tileSize)/gamePanel.tileSize;
				tileID = gamePanel.tileManager.labyrinth[tileCol][neighborColOrRow];
				if(gamePanel.tileManager.tile[tileID].isSolid==true) collision.add(DIR.S);
			}
			if (adjacentTile.equals(DIR.W)) { // Check Western Tile
				neighborColOrRow = (tileCenterX - gamePanel.tileSize)/gamePanel.tileSize;
				tileID = gamePanel.tileManager.labyrinth[neighborColOrRow][tileRow];
				if(gamePanel.tileManager.tile[tileID].isSolid==true) collision.add(DIR.W);
			}
			if (adjacentTile.equals(DIR.E)) { // Check Eastern Tile
				neighborColOrRow = (tileCenterX + gamePanel.tileSize)/gamePanel.tileSize;
				tileID = gamePanel.tileManager.labyrinth[neighborColOrRow][tileRow];
				if(gamePanel.tileManager.tile[tileID].isSolid==true) collision.add(DIR.E);
			}
		}
		
		//System.out.println("collision: "+collision);
		return collision;
	}
	
	public static void printRoutes(ArrayList<Route> routes) {
		int i=1;
		for(Route rt : routes) {
			System.out.println("route: "+i);
			//System.out.println("moves: "+rt.moves);
			System.out.print("moves: ");
			for(DIR dr : rt.moves) {
				System.out.print(dr+" -> ");
			}
			System.out.print("\nstations: ");
			for(Station st : rt.stations) {
				System.out.print("("+st.x+","+st.y+") -> ");
			}
			System.out.print("\n");
			i++;
		}
	}
	
}
