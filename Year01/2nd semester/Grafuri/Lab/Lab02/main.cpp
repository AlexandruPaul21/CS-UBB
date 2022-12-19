#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

typedef struct matrix_node{
    int x,y;
}NODE;

vector<int> Adj[100];

int mat[100][100];

const int INF=(1<<30);

int dist[100];
int p[100];
int vis[100];

char res[100][100];

int moore(int n,int from,int to){
    for(int i=1; i<=n; ++i) dist[i]=INF,p[i]=-1;
    dist[from]=0;
    queue<int> Q;
    Q.push(from);
    while(!Q.empty()){
        int act=Q.front();
        Q.pop();
        vector<int>::iterator it;
        for(it=Adj[act].begin();it!=Adj[act].end();++it){
            if(dist[(*it)]==INF){
                dist[(*it)]=1+dist[act];
                p[(*it)]=act;
                Q.push((*it));
            }
        }
    }

    return dist[to];
}

void reconfig(int from,int dest){
    if(from==dest) {
        cout<<from<<" ";
        return;
    }
    reconfig(from,p[dest]);
    cout<<dest<<" ";
}

void dfs(int node){
    if(vis[node]==1) return;
    else {
        cout<<node<<" ";
        vis[node]=1;
    }
    vector<int>::iterator it;
    for(it=Adj[node].begin(); it!=Adj[node].end(); ++it){
        dfs((*it));
    }
}

void clear_vis(int n){
    for(int i=1; i<=n; ++i){
        vis[i]=0;
    }
}

void bfs(int node){
    queue<int>Q;
    Q.push(node);
    while(!Q.empty()){
        int act=Q.front();
        Q.pop();
        cout<<act<<" ";
        vis[act]=1;
        vector<int>::iterator it;
        for(it=Adj[act].begin(); it!=Adj[act].end(); ++it){
            if(!vis[(*it)]){
                Q.push((*it));
            }
        }
    }
}

bool test(NODE nd,int n,int m){
    return !(nd.x<0 || nd.y<0 || nd.x>n || nd.y>m || mat[nd.x][nd.y]==-1);
}

int dx[]={-1,0,1,0};
int dy[]={0,1,0,-1};

void lee(int n,int m,NODE src, NODE dst){
    queue<NODE> Q;
    Q.push(src);
    mat[src.x][src.y]=1;
    while(!Q.empty()){
        NODE act=Q.front();
        Q.pop();
        NODE nw;
        for(int dir=0; dir<4; ++dir){
            nw.x=act.x+dx[dir];
            nw.y=act.y+dy[dir];
            if(test(nw,n,m) && (mat[nw.x][nw.y]>mat[act.x][act.y]+1 || mat[nw.x][nw.y]==0)){
                mat[nw.x][nw.y]=mat[act.x][act.y]+1;
                Q.push(nw);
            }
        }
    }
}

void road(NODE to,int n,int m){
    if(mat[to.x][to.y]==1) return;
    res[to.x][to.y]='x';
    NODE nw;
    for(int dir=0; dir<4; ++dir){
        nw.x=to.x+dx[dir];
        nw.y=to.y+dy[dir];
        if(test(nw,n,m) && mat[nw.x][nw.y]==mat[to.x][to.y]-1){
            road(nw,n,m);
            return;
        }
    }
}

int main() {
    int op; cin>>op;
    if(op==0) {
        ifstream fin("in.txt");
        int n;
        fin >> n;
        int x, y;
        while (fin >> x >> y) {
            mat[x][y] = 1;
            Adj[x].push_back(y);
        }
        //inchidere tranzitiva
        for (int k = 1; k <= n; ++k) {
            for (int i = 1; i <= n; ++i) {
                for (int j = 1; j <= n; ++j) {
                    if (mat[i][k] && mat[k][j]) {
                        mat[i][j] = 1;
                    }
                }
            }
        }

        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                cout << mat[i][j] << " ";
            }
            cout << "\n";
        }

        int src, dest;
        cout << "Introduceti capetele drumului: ";
        cin >> src >> dest;
        int lng = moore(n, src, dest);
        //cout<<lng<<"\n";
        if (lng != 0) reconfig(src, dest);
        cout << "\n";

        cout << "Introduceti varful de pornire la dfs:";
        int node;
        cin >> node;
        dfs(node);
        cout << "\n";

        clear_vis(n);

        cout << "Introduceti varful de pornire la bfs:";
        cin >> node;
        bfs(node);
        cout << "\n";
        return 0;
    }
    ifstream fin("labirint_1.txt");
    string line;
    NODE from,to;
    int n=0,m=0;
    for(int j=1;getline(fin,line); ++j){
        n=max(n,j);
        for(int i=0; i<line.size(); ++i){
            if(line[i]=='S') {
                from.x=j;
                from.y=i+1;
                continue;
            }
            if(line[i]=='F'){
                to.x=j;
                to.y=i+1;
                continue;
            }
            if(line[i]=='1') mat[j][i+1]=-1;
            else mat[j][i+1]=0;
        }
        m=max(m,(int)line.size());
    }
    lee(n,m,from,to);
    for(int i=1; i<=n; ++i){
        for(int j=1; j<=m; ++j) {
            res[i][j]=' ';
        }
    }
    for(int i=1; i<=n; ++i){
        for(int j=1; j<=m; ++j){
            if(from.x==i && from.y==j){
                res[i][j]='S';
                continue;
            }
            if(to.x==i && to.y==j){
                res[i][j]='F';
                continue;
            }
            if(mat[i][j]==-1){
                res[i][j]='1';
            }
        }
    }
    road(to,n,m);
    res[to.x][to.y]='F';
    for(int i=1; i<=n; ++i){
        for(int j=1; j<=m; ++j){
            cout<<res[i][j];
        }
        cout<<"\n";
    }
    return 0;
}
