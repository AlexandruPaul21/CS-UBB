#include <iostream>
#include <fstream>
#include <vector>
#include <cstdlib>

using namespace std;

ifstream fin("in.txt");

bool visited[100];

int mat_adj[100][100];
int mat_inc[100][100];
vector<int>adj[100];

void dfs(int node){
    visited[node]=1;
    vector<int>::iterator it;
    for(it=adj[node].begin(); it<adj[node].end();++it){
        if(!visited[(*it)]){
            dfs((*it));
        }
    }
}

void roy_floyd(int n){
    for(int k=1; k<=n; ++k){
        for(int i=1; i<=n; ++i){
            for(int j=1; j<=n; ++j){
                if(i!=j)
                if(mat_adj[i][k] && mat_adj[k][j] && (mat_adj[i][j]>mat_adj[i][k]+mat_adj[k][j] || !mat_adj[i][j])){
                    mat_adj[i][j]=mat_adj[i][k]+mat_adj[k][j];
                }
            }
        }
    }
}

int main() {
    int n;
    fin>>n;

    int x,y;
    int nr_muchii=0;
    for (int i=1;fin>>x>>y; ++i){
        mat_adj[x][y]=mat_adj[y][x]=1;
        mat_inc[x][i]=mat_inc[y][i]=1;
        adj[x].push_back(y);
        adj[y].push_back(x);
        nr_muchii=i;
    }

    //cout<<nr_muchii;

    cout<<"Matrice de adiacenta:\n";
    for(int i=1; i<=n; ++i){
        for (int j=1; j<=n; ++j){
            cout<<mat_adj[i][j]<<" ";
        }
        cout<<"\n";
    }

    cout<<"\n";

    cout<<"Matrice de incidenta:\n";
    for(int i=1; i<=n; ++i){
        for (int j=1; j<=nr_muchii; ++j){
            cout<<mat_inc[i][j]<<" ";
        }
        cout<<"\n";
    }

    int isolated=0;
    for(int i=1; i<=n; ++i){
        bool all_0=1;
        for(int j=1; j<=n; ++j){
            if(mat_adj[i][j]==1){
                all_0=0;
            }
        }
        if(all_0==1){
            ++isolated;
        }
    }
    cout<<"\nNr de varfuri izolate: "<<isolated<<"\n";

    bool regular=1;
    int gen_degree=-1;
    for(int i=1; i<=n; ++i){
        int degree=0;
        for(int j=1; j<=n; ++j){
            degree+=mat_adj[i][j];
        }
        if(gen_degree==-1){
            gen_degree=degree;
        }else if (gen_degree!=degree){
            regular=0;
        }
    }

    if(regular==1){
        cout<<"Graful este regulat!\n";
    } else {
        cout<<"Graful nu este regulat!\n";
    }

    dfs(1);
    bool conex=1;
    for(int i=1; i<=n; ++i){
        if(!visited[i]){
            conex=0;
        }
    }
    if(conex==1){
        cout<<"Graful este conex!\n";
    } else {
        cout<<"Graful nu este conex!\n";
    }

    roy_floyd(n);

    cout<<"\nMatrice drumurilor:\n";
    for(int i=1; i<=n; ++i){
        for (int j=1; j<=n; ++j){
            cout<<mat_adj[i][j]<<" ";
        }
        cout<<"\n";
    }

    return 0;
}
