#include <iostream>
#include <algorithm>
#include <fstream>
#include <vector>
#include <unordered_map>
#include <map>

using namespace std;

const int TIME_LIMIT = 10000;

typedef struct Point1 {
    int x, y;
}FrePlanet;

typedef struct Point2{
    int x, y;
    int bonus, strength;
    bool discovered = false;
}HosPlanet;

void readFre(int n0, vector<FrePlanet>& arr, ifstream& fin) {
    for (int i = 0; i < n0; ++i) {
        int x, y;
        fin >> x >> y;
        arr.push_back({x, y});
    }
}

void readHos(int n0, vector<HosPlanet>& arr, ifstream& fin) {
    for (int i = 0; i < n0; ++i) {
        int x, y, bonus, strength;
        fin >> x >> y >> bonus >> strength;
        arr.push_back({x, y, bonus, strength});
    }
}

vector<HosPlanet> removeC(vector<HosPlanet> planets, int max) {
    vector<HosPlanet> res;

    for (auto planet : planets) {
        if (planet.strength <= max) {
            res.push_back(planet);
        }
    }
    return res;
}

int distance(HosPlanet p1, HosPlanet p2) {
    return abs(p1.x - p2.x) + abs(p1.y - p2.y);
}

map<int,pair<int, int>> calc(FrePlanet start, float speed, int strength, vector<HosPlanet>& planets) {
    map<int,pair<int, int>> res;
    res.clear();

    auto temp = removeC(planets, strength);

    HosPlanet act{start.x, start.y, 0, 0};

    std::sort(temp.begin(), temp.end(), [=](HosPlanet& p1, HosPlanet& p2){
        return p1.strength > p2.strength;
    });

    std::sort(temp.begin(), temp.end(), [=](HosPlanet& p1, HosPlanet& p2){
        return distance(act,p1) < distance(act, p2);
    });

    float time = TIME_LIMIT;
    while (time > 0) {
        HosPlanet planet{-1};
        int index = 0;
        int si = 0;
        for (auto& plan: temp) {
            if ( (distance(act, plan) < distance(act, planet) && !plan.discovered)) {
                planet = plan;
                si = index;
            }
            ++index;
        }
        if (planet.x == -1) {
            break;
        }
        temp[si].discovered = true;
        float dst = (float) distance(act, planet) / speed;
        time -= dst;
        if (time < 0) {
            break;
        }
        res[res.size()] = make_pair(planet.x, planet.y);
        act = planet;
    }
    return res;
}

int main() {
    ifstream fin("Level1.in");
    ofstream fout("text.ou");
    srand(time(nullptr));
    vector<FrePlanet> friendPlanet;
    vector<HosPlanet> hostilePlanet;
    int scenarios;
    fin >> scenarios;
    fout << scenarios << "\n";
    while (scenarios--) {
        friendPlanet.clear();
        hostilePlanet.clear();

        int initStrength = 0, initSpeed = 1;
        int budget, n0Friendly, n0Hostile;

        fin >> budget >> n0Friendly >> n0Hostile;

        int speedBudget = (int)(0.55 * budget);
        int strengthBudget = budget - speedBudget;

        int finalStrength = initStrength + (1 * strengthBudget);
        float finalSpeed = initSpeed + (float)(0.1 * speedBudget);

        readFre(n0Friendly, friendPlanet, fin);
        readHos(n0Hostile , hostilePlanet, fin);

        //int startingFriendlyPlanetIndex = random()%n0Friendly;
        int startingFriendlyPlanetIndex = n0Friendly - 1;
        fout << startingFriendlyPlanetIndex << "\n";
        fout << speedBudget << " " << strengthBudget << "\n";

        auto res = calc(friendPlanet[startingFriendlyPlanetIndex], finalSpeed, finalStrength, hostilePlanet);

        fout << res.size() << "\n";
        for (auto elem : res) {
            fout << "M " << elem.second.first << " " << elem.second.second << "\n";
        }

    }




    return 0;
}
