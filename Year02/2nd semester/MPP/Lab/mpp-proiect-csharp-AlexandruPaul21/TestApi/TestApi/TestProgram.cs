using System;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text;
using model;
using Newtonsoft.Json;

namespace Org.Example.Start
{
    public class RestTest
    {
        private const string URL = "http://localhost:8080/api/flight";
        private readonly HttpClient httpClient = new HttpClient();

        private async Task<T> Execute<T>(Func<Task<T>> func)
        {
            try
            {
                return await func();
            }
            catch (HttpRequestException ex) when (ex.StatusCode >= System.Net.HttpStatusCode.BadRequest && ex.StatusCode < System.Net.HttpStatusCode.InternalServerError)
            {
                throw new Exception(ex.Message);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public async Task<Flight[]> GetAll()
        {
            return await Execute(async () =>
            {
                HttpResponseMessage response = await httpClient.GetAsync(URL);
                response.EnsureSuccessStatusCode();
                string responseBody = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<Flight[]>(responseBody);
            });
        }

        public async Task<Flight> GetById(long id)
        {
            return await Execute(async () =>
            {
                HttpResponseMessage response = await httpClient.GetAsync($"{URL}/{id}");
                response.EnsureSuccessStatusCode();
                string responseBody = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<Flight>(responseBody);
            });
        }

        public async Task<Flight> Create(Flight flight)
        {
            return await Execute(async () =>
            {
                HttpResponseMessage response = await httpClient.PostAsJsonAsync(URL, flight);
                response.EnsureSuccessStatusCode();
                string responseBody = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<Flight>(responseBody);
            });
        }


        public async Task Update(Flight flight)
        {
            await Execute(async () =>
            {
                HttpResponseMessage response = await httpClient.PutAsJsonAsync(URL, flight);
                response.EnsureSuccessStatusCode();
                return Task.CompletedTask;
            });
        }

        public async Task Delete(string id)
        {
            await Execute(async () =>
            {
                HttpResponseMessage response = await httpClient.DeleteAsync($"{URL}/{id}");
                response.EnsureSuccessStatusCode();
                return Task.CompletedTask;
            });
        }
    }
}
