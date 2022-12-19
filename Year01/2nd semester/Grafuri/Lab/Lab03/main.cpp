#include <iostream>
#include <vector>
#include <fstream>
#include <queue>
#include <set>

using namespace std;

const int INF=(1<<30);

typedef struct{
    int d;
    int p;
}VERTEX;

typedef struct{
    int from;
    int to;
    int cost;
}NODE;

typedef struct{
    int node;
    int cost;
}OTH_NODE;

bool operator<(const OTH_NODE& a, const OTH_NODE& b){
    return a.cost>b.cost;
}

priority_queue<OTH_NODE> Q;

vector<NODE>A;

vector<OTH_NODE>Adj[1000];
vector<OTH_NODE>Adj1[1000];

vector<VERTEX>dist;
vector<VERTEX>dist1;

void init(int n,int start){
    for(int i=0; i<n; ++i){
        dist[i].d=INF;
        dist[i].p=-1;
    }
    dist[start].d=0;
    dist[start].p=0;

}

void relax(NODE elem){
    if(dist[elem.to].d>dist[elem.from].d+elem.cost){
        dist[elem.to].d=dist[elem.from].d+elem.cost;
        dist[elem.to].p=elem.from;
    }
}

bool BellmanFord(int n,int m,int start){
    init(n,start);

    for(int i=0; i<n-1; ++i){
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

//DIJKSTRA ZONE
bool S[1000];
void Dijkstra(int n,int start){
    init(n,start);
    for(int i=0; i<n; ++i) S[i]=0;
    for(int i=0; i<n; ++i){
        Q.push({i,dist[i].d});
    }
    while(!Q.empty()){
        OTH_NODE u=Q.top();
        Q.pop();
        S[u.node]=1;
        for(auto& it: Adj[u.node]){
            NODE elem={u.node,it.node,it.cost};
            relax(elem);
        }
        while(!Q.empty()){
            Q.pop();
        }
        for(int i=0; i<n; ++i){
            if(!S[i]) {
                Q.push({i, dist[i].d});
            }
        }
    }
}

int main() {
    int op; cin>>op;
    if(op==0) {//BELLMAN-FORD
        int n, m, start;
        cin >> n >> m >> start;
        A.resize(m);
        dist.resize(n);
        for (int i = 0; i < m; ++i) {
            cin >> A[i].from >> A[i].to >> A[i].cost;
        }

        BellmanFord(n, m, start);

        for (int i = 0; i < n; ++i) {
            cout << dist[i].d << " ";
        }
        return 0;
    }

    int n,m;
    cin>>n>>m;
    dist.resize(n+2);
    A.resize(m+2*n);
    for(int i=0; i<m; ++i){
        int x,y,cost;
        cin>>x>>y>>cost;
        A[i].from=x;
        A[i].to=y;
        A[i].cost=cost;
        Adj[x].push_back({y,cost});
    }

    for(int i=0; i<n; ++i){
        A[m+i].from=n;
        A[m+i].to=i;
        A[m+i].cost=0;
    }

    bool suc=BellmanFord(n+1,m+n,n);

    if(!suc){
        cout<<"-1\n";
        return 0;
    }

    A.resize(m);
    //cout<<A.size();

    sort(A.begin(),A.end(),[](NODE& a,NODE& b){if (a.from==b.from) return a.to<b.to; return a.from<b.from;});

    for(int i=0; i<n; ++i){
        Adj[i].clear();
    }

    //ofstream fout("file.out");

    for(int i=0; i<m; ++i){
        cout<<A[i].from<<" "<<A[i].to<<" "<<A[i].cost+dist[A[i].from].d-dist[A[i].to].d<<"\n";
        int new_cost=A[i].cost+dist[A[i].from].d-dist[A[i].to].d;
        Adj[A[i].from].push_back({A[i].to,new_cost});
    }

    dist1=dist;

    for(int i=0; i<n; ++i){
        for(int j=0; j<n; ++j){
            //cout<<1;
            if(i==j){
                cout<<"0 ";
                continue;
            }
            Dijkstra(n,i);
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
