#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

typedef struct{
    int d,p;
}DST;

typedef struct {
    int from,to,cost;
}NODE;

typedef struct {
    int to,cost;
}OTH_NODE;
bool operator<(const OTH_NODE& a,const OTH_NODE& b){
    return a.cost>b.cost;
}

const int INF=(1<<30);

vector<DST> dist;
vector<DST> dist1;
vector<NODE> A;
vector<OTH_NODE> Adj[1000];

void init_list(int start,int n){
    for(int i=0; i<n; ++i){
        dist[i].d=INF;
        dist[i].p=-1;
    }
    dist[start].d=0;
    dist[start].p=-1;
}

void relax(NODE elem){
    if(dist[elem.to].d>dist[elem.from].d+elem.cost){
        dist[elem.to].d=dist[elem.from].d+elem.cost;
        dist[elem.to].p=elem.from;
    }
}

bool BellmanFord(int from,int n,int m){
    init_list(from,n);

    for (int i=0; i<n-1; ++i){
        for(int j=0; j<m; ++j){
            relax(A[j]);
        }
    }

    for(int i=0; i<m; ++i){
        if(dist[A[i].to].d>dist[A[i].from].d+A[i].cost){
            return false;
        }
    }
    return true;
}

bool vis[1000];
void Dijkstra(int n,int start){
    init_list(start,n);
    priority_queue<OTH_NODE>Q;
    for(int i=0; i<n; ++i){
        vis[i]=0;
        Q.push({i,dist[i].d});
    }
    while(!Q.empty()){
        auto act=Q.top();
        Q.pop();
        vis[act.to]=1;
        for(auto& it : Adj[act.to]){
            relax({act.to,it.to,it.cost});
        }
        while(!Q.empty()){
            Q.pop();
        }
        for(int i=0; i<n; ++i){
            if(!vis[i]){
                Q.push({i,dist[i].d});
            }
        }
    }
}

void calc_roads(int n,int m,int start){
    bool vld=BellmanFord(start,n,m);

    if(!vld){
        cout<<"Nu-i bun!\n";
    }

    for(int i=0; i<n; ++i){
        cout<<dist[i].d<<" ";
    }
    cout<<"\n";

    Dijkstra(n,start);

    for(int i=0; i<n; ++i){
        cout<<dist[i].d<<" ";
    }
    cout<<"\n";
}

int main() {
    ifstream fin("test.txt");
    int n,m,start;
    fin>>n>>m>>start;
    dist.resize(n);
    for(int i=0; i<m; ++i){
        int from,to,cost;
        fin>>from>>to>>cost;
        A.push_back({from,to,cost});
        Adj[from].push_back({to,cost});
    }

    //calc_roads(n,m,start);

    dist.resize(n+1);
    for(int i=0; i<n; ++i){
        A.push_back({n,i,0});
    }

    bool vld= BellmanFord(n,n+1,m+n);

    if(!vld){
        cout<<"-1\n";
        return 0;
    }

    sort(A.begin(),A.end(),[](const NODE&a, const NODE&b){
        if(a.from==b.from){
            return a.to<b.to;
        }
        return a.from<b.from;
    });

    for(int i=0; i<n; ++i){
        Adj[i].clear();
    }

    for(int i=0; i<m; ++i){
        int cost=A[i].cost+dist[A[i].from].d-dist[A[i].to].d;
        cout<<A[i].from<<" "<<A[i].to<<" "<<cost<<"\n";
        Adj[A[i].from].push_back({A[i].to,cost});
    }

    dist1=dist;

    for(int i=0; i<n; ++i){
        Dijkstra(n,i);
        for(int j=0; j<n; ++j){
            if(dist[j].d==INF){
                cout<<"INF ";
            } else {
                cout<<dist[j].d-dist1[i].d+dist1[j].d<<" ";
            }
        }
        cout<<"\n";
    }

    return 0;
}
//u->v => c(u->v)+c1(u)-c1(v)
//u->v =15
//u->v => c_j(u->v)-c1(u)+c1(v)
