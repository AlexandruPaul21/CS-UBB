using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    [Serializable]
    public class AgencyDto
    {
        public String username { get; set; }
        public String name { get; set; }
        public String password { get; set; }

        public AgencyDto(String username, String name, String password)
        {
            this.username = username;
            this.name = name;
            this.password = password;
        }
    }
}
