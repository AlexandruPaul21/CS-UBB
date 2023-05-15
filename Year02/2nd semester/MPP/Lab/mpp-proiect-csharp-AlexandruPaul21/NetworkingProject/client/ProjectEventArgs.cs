using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace client
{
    public enum ProjectEvent
    {
        Booked
    }
    public class ProjectEventArgs : EventArgs
    {
        private readonly ProjectEvent userEvent;
        private readonly Object data;

        public ProjectEventArgs(ProjectEvent userEvent, object data)
        {
            this.userEvent = userEvent;
            this.data = data;
        }

        public ProjectEvent UserEventType
        {
            get { return userEvent; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}
