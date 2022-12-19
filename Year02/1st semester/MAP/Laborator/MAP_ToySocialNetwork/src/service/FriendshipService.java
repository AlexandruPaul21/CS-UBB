package service;

import domain.Friendship;
import domain.User;
import domain.validators.ArgumentException;
import domain.validators.UserValidator;
import repository.Repository;
import repository.db.DbFriendshipRepository;
import repository.file.FileFriendshipRepository;
import repository.memory.MemoryFriendshipRepository;

import java.util.*;

public class FriendshipService {
    private DbFriendshipRepository repository;

    public Friendship findOne(Long id) {
        return repository.findOne(id);
    }

    public Iterable<Friendship> findAll() {
        return repository.findAll();
    }

    public FriendshipService(DbFriendshipRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a friendship relation between the ids
     * @param idUser frist user id
     * @param idNewFriend second user id
     * @throws ArgumentException if the operation is not permitted
     */
    public void addFriend(Long idUser, Long idNewFriend) {
        if (idUser == null || idNewFriend == null) {
            throw new ArgumentException("At least one id is not valid");
        }

        boolean ok = repository.areFriends(idUser, idNewFriend);
        if (ok) {
            throw new ArgumentException("You cannot add a friendship that already exists");
        }
        Friendship friendship = new Friendship(idUser,idNewFriend);
        friendship.setId(repository.getLowestFreeId());
        repository.save(friendship);
    }

    /**
     * Deletes a friendship relation
     * @param idUser first id
     * @param idNewFriend second id
     * @throws ArgumentException if the operation is not permitted
     */
    public void removeFriend(Long idUser, Long idNewFriend) {
        if (idUser == null || idNewFriend == null) {
            throw new ArgumentException("At least one id is not valid");
        }

        boolean ok = repository.areFriends(idUser, idNewFriend);
        if (!ok) {
            throw new ArgumentException("You cannot delete a friendship that does not exist");
        }
        repository.delete(idUser, idNewFriend);
    }


//    /**
//     * Calculates the number of communities
//     * @param list an empty list
//     * @return the number of communities and
//     *          in list is a list of communities
//     */
//    public int getCommunities(List<List<User>> list) {
//        int ans = 0;
//        Map<Long, Boolean> visited = new HashMap<Long, Boolean>();
//        for (User user : repository.findAll()) {
//            if(visited.get(user.getId()) == null){
//                List<User> auxList = new ArrayList<User>();
//                dfs(user, visited, auxList);
//                list.add(auxList);
//                ++ans;
//            }
//        }
//        return ans;
//    }
//
//    /**
//     * The graph dfs
//     * @param user the actual user
//     * @param visited the visited map
//     * @param comp the actual community
//     */
//    private void dfs(User user, Map<Long, Boolean> visited, List<User> comp) {
//        visited.put(user.getId(),true);
//        comp.add(user);
//        for (User friend : user.getFriends()) {
//            if(visited.get(friend.getId()) == null) {
//                dfs(friend, visited, comp);
//            }
//        }
//    }
//
//    /**
//     * The graph bfs
//     * @param users the users communities
//     * @param start the start user
//     * @return the longest road in a community
//     */
//    private int bfs(List<User> users, User start) {
//        Map<Long, Integer> distances = new HashMap<>();
//
//        distances.put(start.getId(), 0);
//        Queue<User> userQueue = new LinkedList<>();
//        userQueue.add(start);
//
//        while (!userQueue.isEmpty()) {
//            User user = userQueue.remove();
//            Integer dist = distances.get(user.getId());
//            for (User friend : user.getFriends()) {
//                if (distances.get(friend.getId()) == null) {
//                    distances.put(friend.getId(), 1 + dist);
//                    userQueue.add(friend);
//                } else if (distances.get(friend.getId()) > 1 + dist) {
//                    distances.replace(friend.getId(), 1 + dist);
//                    userQueue.add(friend);
//                }
//            }
//        }
//
//        int ans = 0;
//        for (Map.Entry<Long, Integer> entries : distances.entrySet()) {
//            ans = Integer.max(ans, entries.getValue());
//        }
//
//        return ans;
//    }
//
//    /**
//     * It calculates the most sociable community
//     * @param list the list of communities
//     * @return the most sociable community
//     */
//    public List<User> mostSociableCommunity(List<List<User>> list) {
//        int ansVal = 0;
//        List<User> ansList = new ArrayList<>();
//        for (List<User> listS : list) {
//            int ansInt = 0;
//            for (User user : listS) {
//                ansInt = Integer.max(ansInt, bfs(listS,user));
//            }
//
//            if (ansInt > ansVal) {
//                ansVal = ansInt;
//                ansList = listS;
//            }
//        }
//        return ansList;
//    }
//
//}

}
